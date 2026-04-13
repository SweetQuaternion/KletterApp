package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String keycloakId;

    @NotBlank(message = "Name darf nicht leer sein")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;
    
    private String bio;
    
}