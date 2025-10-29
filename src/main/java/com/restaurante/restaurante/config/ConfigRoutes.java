package com.restaurante.restaurante.config;

import java.util.List;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class ConfigRoutes {

        public static List<RouteConfig> adminRoutes = List.of(
                        RouteConfig.builder()
                                        .methods(List.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE))
                                        .path("api/v1/comidas/**")
                                        .build(),
                        RouteConfig.builder().methods(List.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE))
                                        .path("api/v1/categorias/**").build(),
                        RouteConfig.builder().methods(List.of(HttpMethod.POST, HttpMethod.PUT, HttpMethod.DELETE))
                                        .path("api/v1/mesas/**").build(),
                        RouteConfig.builder().methods(List.of(HttpMethod.GET)).path("api/v1/usuarios").build());
}
