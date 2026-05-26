package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WandCreateDTO {
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int hallenId;

    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int wandNr;

    private String name;
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private float startX;
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private float startY;
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private float endX;
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private float endY;
    
    private String position;

}
