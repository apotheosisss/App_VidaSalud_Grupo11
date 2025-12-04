package com.vidasalud.backend.controller;

import com.vidasalud.backend.model.RegistroAlimentacion;
import com.vidasalud.backend.model.RegistroSueno;
import com.vidasalud.backend.repository.RegistroAlimentacionRepository;
import com.vidasalud.backend.repository.RegistroSuenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/datos")
public class DatosController {

    @Autowired
    private RegistroSuenoRepository suenoRepository;

    @Autowired
    private RegistroAlimentacionRepository alimentacionRepository;

    // --- ENDPOINTS DE SUEÑO ---

    @PostMapping("/sueno")
    public RegistroSueno guardarSueno(@RequestBody RegistroSueno registro) {
        registro.setFecha(LocalDate.now()); // Asigna la fecha de hoy automáticamente
        return suenoRepository.save(registro);
    }

    @GetMapping("/sueno/{usuarioId}")
    public List<RegistroSueno> obtenerSueno(@PathVariable Long usuarioId) {
        return suenoRepository.findByUsuarioId(usuarioId);
    }

    // --- ENDPOINTS DE ALIMENTACIÓN ---

    @PostMapping("/alimentacion")
    public RegistroAlimentacion guardarAlimentacion(@RequestBody RegistroAlimentacion registro) {
        registro.setFecha(LocalDate.now());
        return alimentacionRepository.save(registro);
    }

    @GetMapping("/alimentacion/{usuarioId}")
    public List<RegistroAlimentacion> obtenerAlimentacion(@PathVariable Long usuarioId) {
        return alimentacionRepository.findByUsuarioId(usuarioId);
    }
}