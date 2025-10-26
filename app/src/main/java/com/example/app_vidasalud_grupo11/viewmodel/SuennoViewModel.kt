package com.example.app_vidasalud_grupo11.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Clase de datos para agrupar el estado de la pantalla de Sueño
data class SuennoUiState( // Estado de la UI del Sueño
    val diasContador: Int = 0,
    val sleepHours: List<Float> = emptyList() // Lista de horas de sueño
)

class SuennoViewModel : ViewModel() {

    // El estado de la UI, privado y mutable
    private val _uiState = MutableStateFlow(SuennoUiState()) // Estado de la UI del Sueño

    // El estado público e inmutable que la UI observará
    val uiState: StateFlow<SuennoUiState> = _uiState.asStateFlow()

    // El texto del campo de input, también sobrevive a la configuración
    var horasInput by mutableStateOf("") // Campo de texto para horas de sueño
        private set // No se puede modificar desde fuera



    // Actualiza el valor del campo de texto
    fun onHorasInputChange(newValue: String) {
        horasInput = newValue
    }

    // logica para añadir nuevas horas de sueño
    fun addSleepData() { // Añadir nueva hora de sueño
        val hours = horasInput.toFloatOrNull() ?: return // Salir si el input no es un número valido

        val currentState = _uiState.value

        // logica de reseteo cada 7 días
        val newState = if (currentState.diasContador >= 7) {
            // Reinicia la lista y el contador, y añade la nueva hora como el primer día del nuevo ciclo
            SuennoUiState(diasContador = 1, sleepHours = listOf(hours))
        } else {
            // añade la nueva hora al ciclo actual
            currentState.copy(
                diasContador = currentState.diasContador + 1,
                sleepHours = currentState.sleepHours + hours
            )
        }

        _uiState.value = newState // Actualizar el estado con el nuevo valor


        // Limpiar el campo de texto despues de guardar
        horasInput = ""
    }
}
