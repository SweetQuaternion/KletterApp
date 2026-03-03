package com.dachpc.kletterapp.Entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
@JsonPropertyOrder({"id", "name", "email"})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "passwort_hash")
    private String password;

    @Column(name = "bild_url")
    private String bildUrl;

    @Column(name = "role")
    private String role;

    // Getters and setters
    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getBildUrl() { return bildUrl; }
    public String getRole() { return role; }

    public void setId(int id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPassword(String password) { this.password = password; }
    public void setBildUrl(String bildUrl) { this.bildUrl = bildUrl; }
    public void setRole(String role) { this.role = role; }
}