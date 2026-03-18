package com.dachpc.kletterapp.Entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// können wir in andere Klassen einbetten oder so
@Embeddable
public class WandId implements Serializable{

    @Column(name = "hallen_id")
    private Integer hallenId;

    @Column(name = "wand_nr")
    private Integer wandNr;

    public WandId() {
    }

    public WandId(Integer hallenId, Integer wandNr) {
        this.hallenId = hallenId;
        this.wandNr = wandNr;
    }

    public Integer getHallenId() { return hallenId; }
    public Integer getWandNr() { return wandNr; }

    public void setHallenId(Integer hallenId) { this.hallenId = hallenId; }
    public void setWandNr(Integer wandNr) { this.wandNr = wandNr; }

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
