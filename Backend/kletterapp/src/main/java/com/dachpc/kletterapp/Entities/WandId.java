package com.dachpc.kletterapp.Entities;

import java.io.Serializable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// können wir in andere Klassen einbetten oder so
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WandId implements Serializable{

    @Column(name = "hallen_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int hallenId;

    @Column(name = "wand_nr")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int wandNr;
    
}
