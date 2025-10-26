package com.restaurante.restaurante.utils;

import com.fasterxml.jackson.annotation.JsonView;
import com.restaurante.restaurante.view.Views;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonView({ Views.ComidaView.class, Views.CategoriaView.class })
public class ApiResponse<T> {
    private int code;

    private String message;

    private T data;
}