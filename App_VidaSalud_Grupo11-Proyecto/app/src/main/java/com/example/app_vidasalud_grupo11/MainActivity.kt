package com.example.app_vidasalud_grupo11

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Surface
import com.example.app_vidasalud_grupo11.ui.screens.AppNavigation
import com.example.app_vidasalud_grupo11.ui.theme.App_VidaSalud_Grupo11Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App_VidaSalud_Grupo11Theme { // Utiliza el tema personalizado
                // Configura la superficie para el tema personalizado
                Surface {
                    AppNavigation() // Utiliza el composable AppNavigation
                }
            }
        }
    }
}