package com.example.app_vidasalud_grupo11.model

data class RegistroSueno(
    val id: Long? = null,
    val usuarioId: Long,
    val fecha: String? = null, // Usaremos String para simplificar la fecha (formato "yyyy-MM-dd")
    val horas: Float
)