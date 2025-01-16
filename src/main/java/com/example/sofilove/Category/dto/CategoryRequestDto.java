package com.example.sofilove.category.dto;


import lombok.Getter;
import lombok.Setter;

import jakarta.validation.constraints.*;


@Getter
@Setter
public class CategoryRequestDto {
    @NotNull
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotNull
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String description;
}
