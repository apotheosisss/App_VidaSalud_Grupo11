package com.vidasalud.backend.repository;

import com.vidasalud.backend.model.RegistroSueno;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroSuenoRepository extends JpaRepository<RegistroSueno, Long> {
    List<RegistroSueno> findByUsuarioId(Long usuarioId);
}