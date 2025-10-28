package com.restaurante.restaurante.config;

import java.util.List;

import org.springframework.http.HttpMethod;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class RouteConfig {
    private List<HttpMethod> methods;
    private String path;

    public List<HttpMethod> getMethods() {
        return methods;
    }

    public String getPath() {
        return path;
    }
}
