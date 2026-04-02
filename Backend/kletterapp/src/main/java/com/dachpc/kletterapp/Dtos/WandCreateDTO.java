package com.dachpc.kletterapp.Dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class WandCreateDTO {
    
    @NotNull
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private int hallenId;

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
