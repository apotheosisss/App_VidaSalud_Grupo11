package com.example.app_vidasalud_grupo11.model

// Estructura de la respuesta de https://api.adviceslip.com/advice
data class ConsejoResponse(
    val slip: Slip
)

data class Slip(
    val id: Int,
    val advice: String // El consejo en texto
)