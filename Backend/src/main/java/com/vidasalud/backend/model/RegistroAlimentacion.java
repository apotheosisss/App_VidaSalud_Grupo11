package com.vidasalud.backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class RegistroAlimentacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long usuarioId;
    private LocalDate fecha;
    private Float calorias;
    private Float proteinas;
    private Float grasas;
}