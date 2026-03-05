package com.dachpc.kletterapp.Entities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

// können wir in andere Klassen einbetten oder so
@Embeddable
public class UserRoutenStatusId implements Serializable{

    @Column(name = "user_id")
    private int userId;
    
    @Column(name = "route_id")
    private int routeId;

    public UserRoutenStatusId() {
    }

    public UserRoutenStatusId(int userId, int routeId) {
        this.userId = userId;
        this.routeId = routeId;
    }

    public int getUserId() { return userId; }
    public int getRouteId() { return routeId; }

    public void setUserId(int userId) { this.userId = userId; }
    public void setRouteId(int routenId) { this.routeId = routenId; }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || !(other instanceof UserRoutenStatusId)) return false;
        UserRoutenStatusId that = (UserRoutenStatusId) other;
        if (this.userId != that.userId) return false;
        if (this.routeId != that.routeId) return false;
        return true;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(userId, routeId);
    }
    
}
