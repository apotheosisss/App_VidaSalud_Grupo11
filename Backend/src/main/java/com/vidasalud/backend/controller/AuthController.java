package com.vidasalud.backend.controller;

import com.vidasalud.backend.model.Usuario;
import com.vidasalud.backend.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/register")
    public Usuario registrar(@RequestBody Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Usuario usuario) {
        return usuarioRepository.findByUsernameAndPassword(usuario.getUsername(), usuario.getPassword())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(401).build());
    }
}