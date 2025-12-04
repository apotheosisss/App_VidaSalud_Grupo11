package com.example.app_vidasalud_grupo11.model

data class User(
    val id: Long? = null, // El ID puede ser nulo al registrarse
    val username: String,
    val email: String,
    val password: String
)