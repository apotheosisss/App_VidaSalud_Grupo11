package com.example.app_vidasalud_grupo11.viewmodel

import com.example.app_vidasalud_grupo11.MainDispatcherRule
import com.example.app_vidasalud_grupo11.model.RegistroSueno
import com.example.app_vidasalud_grupo11.model.User
import com.example.app_vidasalud_grupo11.model.UserSession
import com.example.app_vidasalud_grupo11.network.ApiService
import com.example.app_vidasalud_grupo11.network.RetrofitClient
import io.mockk.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SuennoViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    // Mocks: Objetos falsos que controlaremos
    private lateinit var apiService: ApiService
    private lateinit var viewModel: SuennoViewModel

    @Before
    fun setup() {
        // 1. Mockeamos los objetos estáticos (Singletons)
        mockkObject(RetrofitClient)
        mockkObject(UserSession)

        // 2. Creamos el servicio falso de la API
        apiService = mockk()

        // 3. Le decimos a RetrofitClient que devuelva nuestro servicio falso
        every { RetrofitClient.instance } returns apiService

        // 4. Simulamos un usuario logueado en la sesión
        UserSession.user = User(id = 1L, username = "TestUser", email = "test@test.com", password = "123")
    }

    @After
    fun tearDown() {
        // Limpiamos los mocks después de cada test
        unmockkAll()
    }

    @Test
    fun `al iniciar el ViewModel, debe cargar datos de sueno desde la API`() = runTest {
        // GIVEN (Dado): Una lista de registros que la API "devolverá"
        val listaFicticia = listOf(
            RegistroSueno(id = 1, usuarioId = 1, horas = 7.5f),
            RegistroSueno(id = 2, usuarioId = 1, horas = 8.0f)
        )
        // Configuramos el mock: Cuando pidan obtenerSueno(1), devuelve la listaFicticia
        coEvery { apiService.obtenerSueno(1L) } returns listaFicticia

        // WHEN (Cuando): Inicializamos el ViewModel (el init llama a fetchSleepData)
        viewModel = SuennoViewModel()

        // THEN (Entonces): El estado de la UI debe reflejar esos datos
        val estadoActual = viewModel.uiState.value

        // Verificamos que hay 2 registros
        assertEquals(2, estadoActual.diasContador)
        // Verificamos que las horas son correctas
        assertEquals(listOf(7.5f, 8.0f), estadoActual.sleepHours)

        // Verificamos que la función de la API fue llamada exactamente una vez
        coVerify(exactly = 1) { apiService.obtenerSueno(1L) }
    }

    @Test
    fun `addSleepData debe enviar datos a la API y recargar la lista`() = runTest {
        // GIVEN:
        // 1. Configuramos el mock para obtenerSueno (se llama al recargar)
        coEvery { apiService.obtenerSueno(1L) } returns emptyList()
        // 2. Configuramos el mock para guardarSueno (debe devolver el objeto guardado)
        coEvery { apiService.guardarSueno(any()) } returns RegistroSueno(1, 1, null, 6.0f)

        // Inicializamos
        viewModel = SuennoViewModel()

        // Simulamos que el usuario escribe "6" en el input
        viewModel.onHorasInputChange("6")

        // WHEN: El usuario presiona guardar
        viewModel.addSleepData()

        // THEN:
        // Verificamos que se llamó a guardarSueno con las horas correctas (6.0f)
        coVerify {
            apiService.guardarSueno(match { it.horas == 6.0f && it.usuarioId == 1L })
        }
        // Verificamos que el input se limpió
        assertEquals("", viewModel.horasInput)
    }
}