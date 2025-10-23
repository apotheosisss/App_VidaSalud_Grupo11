package com.example.app_vidasalud_grupo11.viewmodel

import androidx.lifecycle.ViewModel
import com.example.app_vidasalud_grupo11.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LoginViewModel : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users

    fun register(user: User) {
        _users.value = _users.value + user
    }

    fun login(username: String, pass: String): Boolean {
        val user = _users.value.find { it.username == username && it.password == pass }
        return user != null
    }
}