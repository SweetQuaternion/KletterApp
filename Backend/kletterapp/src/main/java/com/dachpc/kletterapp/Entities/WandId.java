package com.dachpc.kletterapp.Entities;

import java.io.Serializable;
import java.util.Objects;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// können wir in andere Klassen einbetten oder so
@Embeddable
public class WandId implements Serializable{

    @Column(name = "hallen_id")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int hallenId;

    @Column(name = "wand_nr")
    @Schema(nullable = false, requiredMode = Schema.RequiredMode.REQUIRED)
    private int wandNr;

    public WandId() {
    }

    public WandId(int hallenId, int wandNr) {
        this.hallenId = hallenId;
        this.wandNr = wandNr;
    }

    public int getHallenId() { return hallenId; }
    public int getWandNr() { return wandNr; }

    public void setHallenId(int hallenId) { this.hallenId = hallenId; }
    public void setWandNr(int wandNr) { this.wandNr = wandNr; }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof WandId)) return false;
        WandId that = (WandId) other;
        if (this.hallenId != that.hallenId) return false;
        if (this.wandNr != that.wandNr) return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(hallenId, wandNr);
    }
    
}
