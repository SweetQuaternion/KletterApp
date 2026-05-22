package com.dachpc.kletterapp.Dtos;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class NewsResponseDTO {

    @NotNull
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int id;

    private Integer hallenId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String autor;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime datum;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String titel;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String inhalt;
}
