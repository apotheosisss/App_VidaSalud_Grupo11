package com.vidasalud.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class RegistroSueno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId; // Relación simple con el usuario
    private LocalDate fecha;
    private Float horas;
}