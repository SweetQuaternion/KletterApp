package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsCreateDTO {
    
    private Integer hallenId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String autorId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String titel;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String inhalt;

}
