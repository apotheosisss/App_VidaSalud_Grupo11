package com.example.app_vidasalud_grupo11.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_vidasalud_grupo11.model.RegistroSueno
import com.example.app_vidasalud_grupo11.model.UserSession
import com.example.app_vidasalud_grupo11.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class SuennoUiState(
    val diasContador: Int = 0,
    val sleepHours: List<Float> = emptyList()
)

class SuennoViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SuennoUiState())
    val uiState: StateFlow<SuennoUiState> = _uiState.asStateFlow()

    var horasInput by mutableStateOf("")
        private set

    init {
        // Carga los datos apenas se crea la pantalla
        fetchSleepData()
    }

    fun onHorasInputChange(newValue: String) {
        horasInput = newValue
    }

    // --- CARGAR DESDE API ---
    private fun fetchSleepData() {
        val userId = UserSession.user?.id ?: return

        viewModelScope.launch {
            try {
                val registros = RetrofitClient.instance.obtenerSueno(userId)
                val horasList = registros.map { it.horas }

                _uiState.value = SuennoUiState(
                    diasContador = horasList.size,
                    sleepHours = horasList
                )
            } catch (e: Exception) {
                Log.e("SuennoViewModel", "Error al cargar datos", e)
            }
        }
    }

    // --- GUARDAR EN API ---
    fun addSleepData() {
        val hours = horasInput.toFloatOrNull() ?: return
        val userId = UserSession.user?.id ?: return

        viewModelScope.launch {
            try {
                val nuevoRegistro = RegistroSueno(
                    usuarioId = userId,
                    horas = hours
                )
                RetrofitClient.instance.guardarSueno(nuevoRegistro)

                // Limpiamos y recargamos para ver el cambio
                horasInput = ""
                fetchSleepData()
            } catch (e: Exception) {
                Log.e("SuennoViewModel", "Error al guardar sueño", e)
            }
        }
    }
}