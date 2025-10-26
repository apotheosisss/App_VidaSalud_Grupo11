package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.app_vidasalud_grupo11.viewmodel.AlimentacionViewModel
import com.example.app_vidasalud_grupo11.viewmodel.LoginViewModel
import com.example.app_vidasalud_grupo11.viewmodel.SuennoViewModel

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val loginViewModel: LoginViewModel = viewModel()
    val suennoViewModel: SuennoViewModel = viewModel()
    val alimentacionViewModel: AlimentacionViewModel = viewModel()

    NavHost(navController = navController, startDestination = "login") { // CAMBIAR A "login"

        composable("login") {
            LoginScreen(navController = navController, loginViewModel = loginViewModel)
        }
        composable("register") {
            RegisterScreen(navController = navController, loginViewModel = loginViewModel)
        }

        val screens = listOf("inicio", "sueno", "consejos", "alimentacion", "seguridad") // lista de las diferentes pantallas


        screens.forEach { screenName -> //recorre la lista de pantallas
            composable(
                route = "$screenName/{username}", // ruta de la pantalla
                arguments = listOf(navArgument("username") { type = NavType.StringType }) // argumento de la ruta
            ) { backStackEntry -> // código de la pantalla
                val username = backStackEntry.arguments?.getString("username") // obtener el argumento de la ruta
                if (username != null) {
                    when (screenName) {
                        "inicio" -> InicioScreen(navController = navController, username = username)
                        "sueno" -> SuennoScreen(navController = navController, username = username, suennoViewModel = suennoViewModel)
                        "consejos" -> ConsejosScreen(navController = navController, username = username)

                        "alimentacion" -> AlimentacionScreen(navController = navController, username = username, alimentacionViewModel = alimentacionViewModel)
                        "seguridad" -> SeguridadScreen(navController = navController, username = username)
                    }
                }
            }
        }
    }
}
