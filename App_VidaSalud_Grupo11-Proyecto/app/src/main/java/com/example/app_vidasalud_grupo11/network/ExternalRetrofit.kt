package com.example.app_vidasalud_grupo11.network

import com.example.app_vidasalud_grupo11.model.ConsejoResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

// 1. La Interfaz con el endpoint
interface ExternalApiService {
    @GET("advice")
    suspend fun obtenerConsejoAleatorio(): ConsejoResponse
}

// 2. El Cliente Retrofit configurado para la web externa
object ExternalRetrofitClient {
    private const val BASE_URL = "https://api.adviceslip.com/"

    val instance: ExternalApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExternalApiService::class.java)
    }
}