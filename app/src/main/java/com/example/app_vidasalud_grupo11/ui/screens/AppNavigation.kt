package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.app_vidasalud_grupo11.viewmodel.LoginViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, loginViewModel = loginViewModel)
        }
    }
}
