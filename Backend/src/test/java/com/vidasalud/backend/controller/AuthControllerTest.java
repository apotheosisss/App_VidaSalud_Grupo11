package com.vidasalud.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.vidasalud.backend.model.Usuario;
import com.vidasalud.backend.repository.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ObjectMapper mapper;

    //crear un usuario JSON
    private String toJson(Object obj) throws Exception {
        return mapper.writeValueAsString(obj);
    }

    // ---PRUEBAS---

    //prueba registrar usuario
    @Test
    void deberiaRegistrarUsuario() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("brian");
        usuario.setEmail("correo@example.com");
        usuario.setPassword("1234");

        when(usuarioRepository.save(any(Usuario.class))).thenReturn(usuario);

        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.username").value("brian"));

        verify(usuarioRepository, times(1)).save(any(Usuario.class));
    }

    // prueba login

    @Test
    void deberiaHacerLoginExitoso() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("brian");
        usuario.setPassword("1234");

        Usuario usuarioDB = new Usuario();
        usuarioDB.setId(1L);
        usuarioDB.setUsername("brian");
        usuarioDB.setPassword("1234");

        when(usuarioRepository.findByUsernameAndPassword("brian", "1234"))
                .thenReturn(Optional.of(usuarioDB));

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(usuario)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("brian"));

        verify(usuarioRepository).findByUsernameAndPassword("brian", "1234");
    }

    // login con credenciales erroneas

    @Test
    void deberiaFallarLoginConCredencialesIncorrectas() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setUsername("brian");
        usuario.setPassword("aodk"); //contraseña incorrecta, al ingresar contraseña correcta la prueba da error.

        when(usuarioRepository.findByUsernameAndPassword("brian", "wrongpass"))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(toJson(usuario)))
                .andExpect(status().isUnauthorized());

        verify(usuarioRepository).findByUsernameAndPassword("brian", "wrongpass");
    }
}
