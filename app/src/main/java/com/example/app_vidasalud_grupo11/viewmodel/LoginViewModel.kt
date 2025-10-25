package com.example.app_vidasalud_grupo11.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_vidasalud_grupo11.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    val admin=User("admin","admin","admin") // Crea un usuario administrador



    fun register(user: User) { // Está función agrega un usuario a la lista de usuarios
        _users.value = _users.value + user
    }

    fun login(username: String, pass: String): Boolean {
        _users.value = _users.value + admin // Agrega el usuario administrador a la lista de usuarios
        val user = _users.value.find { it.username == username && it.password == pass } // busca un usuario en la lista de users
        return user != null
    }
}