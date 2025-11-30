package com.example.app_vidasalud_grupo11.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.app_vidasalud_grupo11.model.User
import com.example.app_vidasalud_grupo11.model.UserSession
import com.example.app_vidasalud_grupo11.network.RetrofitClient

class LoginViewModel : ViewModel() {

    suspend fun login(username: String, pass: String): Boolean {
        return try {
            val user = User(username = username, email = "", password = pass)
            val response = RetrofitClient.instance.login(user)

            if (response.isSuccessful && response.body() != null) {
                // --- AQUÍ GUARDAMOS LA SESIÓN ---
                UserSession.user = response.body()
                Log.d("LoginViewModel", "Login exitoso: ${response.body()}")
                true
            } else {
                Log.e("LoginViewModel", "Error en login: ${response.code()}")
                false
            }
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Excepción en login", e)
            false
        }
    }

    suspend fun register(user: User): Boolean {
        return try {
            val createdUser = RetrofitClient.instance.register(user)
            Log.d("LoginViewModel", "Registro exitoso: $createdUser")
            true
        } catch (e: Exception) {
            Log.e("LoginViewModel", "Error en registro", e)
            false
        }
    }
}
