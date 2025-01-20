package com.example.sofilove.Category.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class    CategoryRequestDto {
    @NotNull
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String name;

    @NotNull
    @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
    private String description;
}
