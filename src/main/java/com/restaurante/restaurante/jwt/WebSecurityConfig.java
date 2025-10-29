package com.restaurante.restaurante.jwt;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.restaurante.restaurante.auth.enums.RoleEnum;
import com.restaurante.restaurante.config.ConfigRoutes;
import com.restaurante.restaurante.config.RouteConfig;
import com.restaurante.restaurante.exceptions.AuthExceptionHandler;

import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthExceptionHandler authExceptionHandler;

    public WebSecurityConfig(AuthenticationProvider authenticationProvider, JwtAuthenticationFilter jwtAuthFilter,
            AuthExceptionHandler authExceptionHandler) {
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthFilter = jwtAuthFilter;
        this.authExceptionHandler = authExceptionHandler;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf(t -> t.disable())
                .authorizeHttpRequests(
                        auth -> {
                            auth.requestMatchers("api/v1/auth/**", "/images/**").permitAll();
                            for (RouteConfig r : ConfigRoutes.adminRoutes) {
                                r.getMethods().forEach(m -> auth.requestMatchers(m, r.getPath())
                                        .hasAuthority(RoleEnum.ADMIN.name()));
                            }
                            auth.anyRequest().authenticated();
                        })
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .accessDeniedHandler(authExceptionHandler));
        return http.build();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("*");
        configuration.addAllowedMethod("*");
        configuration.setAllowedHeaders(List.of("*"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
