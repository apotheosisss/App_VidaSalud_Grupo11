package com.vidasalud.backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data // Lombok genera Getters, Setters y toString automáticamente
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String email;
    private String password;
}