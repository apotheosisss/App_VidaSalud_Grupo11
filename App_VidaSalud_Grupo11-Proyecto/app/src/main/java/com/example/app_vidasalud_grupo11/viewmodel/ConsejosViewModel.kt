package com.example.app_vidasalud_grupo11.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_vidasalud_grupo11.network.ExternalRetrofitClient
import kotlinx.coroutines.launch

class ConsejosViewModel : ViewModel() {

    // Estado que guarda el texto del consejo
    var consejoActual by mutableStateOf("Cargando consejo...")
        private set

    init {
        obtenerNuevoConsejo()
    }

    fun obtenerNuevoConsejo() {
        viewModelScope.launch {
            try {
                consejoActual = "Consultando API externa..."
                val respuesta = ExternalRetrofitClient.instance.obtenerConsejoAleatorio()
                consejoActual = respuesta.slip.advice // Extraemos el texto
            } catch (e: Exception) {
                consejoActual = "Error al cargar consejo. Verifica tu conexión."
            }
        }
    }
}