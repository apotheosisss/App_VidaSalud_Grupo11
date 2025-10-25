package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
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

        // --- AQUÍ ESTÁ EL CAMBIO ---
        composable(
            route = "inicio/{username}", // 1. La ruta ahora define un "placeholder"
            arguments = listOf(navArgument("username") { type = NavType.StringType }) // 2. Se declara el argumento
        ) { backStackEntry -> // 3. Obtenemos acceso a los argumentos
            // 4. Extraemos el argumento de la ruta
            val username = backStackEntry.arguments?.getString("username")

            // 5. Se lo pasamos a InicioScreen
            if (username != null) {
                InicioScreen(navController = navController, username = username)
            }
        }
    }
}
