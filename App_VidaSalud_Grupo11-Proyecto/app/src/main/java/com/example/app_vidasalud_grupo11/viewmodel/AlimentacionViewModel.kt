package com.example.app_vidasalud_grupo11.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app_vidasalud_grupo11.model.RegistroAlimentacion
import com.example.app_vidasalud_grupo11.model.UserSession
import com.example.app_vidasalud_grupo11.network.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AlimentacionUiState(
    val diasContador: Int = 0,
    val caloriasList: List<Float> = emptyList(),
    val proteinasList: List<Float> = emptyList(),
    val grasasList: List<Float> = emptyList()
)

class AlimentacionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(AlimentacionUiState())
    val uiState: StateFlow<AlimentacionUiState> = _uiState.asStateFlow()

    var caloriasInput by mutableStateOf("")
        private set
    var proteinasInput by mutableStateOf("")
        private set
    var grasasInput by mutableStateOf("")
        private set

    init {
        fetchAlimentacionData()
    }

    fun onCaloriasInputChange(newValue: String) { caloriasInput = newValue }
    fun onProteinasInputChange(newValue: String) { proteinasInput = newValue }
    fun onGrasasInputChange(newValue: String) { grasasInput = newValue }

    private fun fetchAlimentacionData() {
        val userId = UserSession.user?.id ?: return

        viewModelScope.launch {
            try {
                val registros = RetrofitClient.instance.obtenerAlimentacion(userId)

                _uiState.value = AlimentacionUiState(
                    diasContador = registros.size,
                    caloriasList = registros.map { it.calorias },
                    proteinasList = registros.map { it.proteinas },
                    grasasList = registros.map { it.grasas }
                )
            } catch (e: Exception) {
                Log.e("AlimentacionViewModel", "Error al cargar datos", e)
            }
        }
    }

    fun addAlimentacionData() {
        val cal = caloriasInput.toFloatOrNull() ?: 0f
        val prot = proteinasInput.toFloatOrNull() ?: 0f
        val gra = grasasInput.toFloatOrNull() ?: 0f
        val userId = UserSession.user?.id ?: return

        viewModelScope.launch {
            try {
                val registro = RegistroAlimentacion(
                    usuarioId = userId,
                    calorias = cal,
                    proteinas = prot,
                    grasas = gra
                )
                RetrofitClient.instance.guardarAlimentacion(registro)

                caloriasInput = ""
                proteinasInput = ""
                grasasInput = ""
                fetchAlimentacionData()
            } catch (e: Exception) {
                Log.e("AlimentacionViewModel", "Error al guardar alimentación", e)
            }
        }
    }
}