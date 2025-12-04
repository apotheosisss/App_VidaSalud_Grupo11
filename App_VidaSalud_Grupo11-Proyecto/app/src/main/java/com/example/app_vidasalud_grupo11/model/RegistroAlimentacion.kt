package com.example.app_vidasalud_grupo11.model

data class RegistroAlimentacion(
    val id: Long? = null,
    val usuarioId: Long,
    val fecha: String? = null,
    val calorias: Float,
    val proteinas: Float,
    val grasas: Float
)