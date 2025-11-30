package com.vidasalud.backend.repository;

import com.vidasalud.backend.model.RegistroAlimentacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroAlimentacionRepository extends JpaRepository<RegistroAlimentacion, Long> {
    List<RegistroAlimentacion> findByUsuarioId(Long usuarioId);
}