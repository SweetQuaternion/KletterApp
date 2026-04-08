package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HalleCreateDTO {

    @NotBlank(message = "Name darf nicht leer sein")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String name;

    @NotBlank(message = "Adresse darf nicht leer sein")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String adresse;

    private String betreiber;
    
}
