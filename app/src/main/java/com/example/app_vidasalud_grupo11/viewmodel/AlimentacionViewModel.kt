package com.example.app_vidasalud_grupo11.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// Clase de datos para agrupar el estado de la pantalla de Alimentación
data class AlimentacionUiState(
    val diasContador: Int = 0,
    val caloriasList: List<Float> = emptyList(),
    val proteinasList: List<Float> = emptyList(),
    val grasasList: List<Float> = emptyList()
)

class AlimentacionViewModel : ViewModel() {

    // --- ESTADO ---
    private val _uiState = MutableStateFlow(AlimentacionUiState())

    val uiState: StateFlow<AlimentacionUiState> = _uiState.asStateFlow()

    // Estado para los campos de texto
    var caloriasInput by mutableStateOf("")
        private set
    var proteinasInput by mutableStateOf("")
        private set
    var grasasInput by mutableStateOf("")
        private set

    // --- INTENCIONES (Eventos de la UI) ---

    fun onCaloriasInputChange(newValue: String) {
        caloriasInput = newValue
    }

    fun onProteinasInputChange(newValue: String) {
        proteinasInput = newValue
    }

    fun onGrasasInputChange(newValue: String) {
        grasasInput = newValue
    }

    // logica para añadir los datos de nutrición del día
    fun addAlimentacionData() {
        // Convertir inputs a numeros, si no son validos, usar 0.0f
        val calorias = caloriasInput.toFloatOrNull() ?: 0.0f
        val proteinas = proteinasInput.toFloatOrNull() ?: 0.0f
        val grasas = grasasInput.toFloatOrNull() ?: 0.0f

        val currentState = _uiState.value

        val newState = if (currentState.diasContador >= 7) {
            // Reinicia el estado para un nuevo ciclo de 7 días
            AlimentacionUiState(
                diasContador = 1,
                caloriasList = listOf(calorias),
                proteinasList = listOf(proteinas),
                grasasList = listOf(grasas)
            )
        } else {
            // Añade los datos al ciclo actual
            currentState.copy(
                diasContador = currentState.diasContador + 1,
                caloriasList = currentState.caloriasList + calorias,
                proteinasList = currentState.proteinasList + proteinas,
                grasasList = currentState.grasasList + grasas
            )
        }

        _uiState.value = newState

        // Limpiar campos después de guardar
        caloriasInput = ""
        proteinasInput = ""
        grasasInput = ""
    }
}
