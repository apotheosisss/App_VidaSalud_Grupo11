package com.example.app_vidasalud_grupo11.network

import com.example.app_vidasalud_grupo11.model.RegistroAlimentacion
import com.example.app_vidasalud_grupo11.model.RegistroSueno
import com.example.app_vidasalud_grupo11.model.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    // --- Auth ---
    @POST("auth/login")
    suspend fun login(@Body user: User): Response<User>

    @POST("auth/register")
    suspend fun register(@Body user: User): User

    // --- Sueño ---
    @POST("datos/sueno")
    suspend fun guardarSueno(@Body registro: RegistroSueno): RegistroSueno

    @GET("datos/sueno/{usuarioId}")
    suspend fun obtenerSueno(@Path("usuarioId") usuarioId: Long): List<RegistroSueno>

    // --- Alimentación ---
    @POST("datos/alimentacion")
    suspend fun guardarAlimentacion(@Body registro: RegistroAlimentacion): RegistroAlimentacion

    @GET("datos/alimentacion/{usuarioId}")
    suspend fun obtenerAlimentacion(@Path("usuarioId") usuarioId: Long): List<RegistroAlimentacion>
}