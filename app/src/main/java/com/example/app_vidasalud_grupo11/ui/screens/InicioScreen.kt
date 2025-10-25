package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class) // Necesario para TopAppBar, (Topappbar permite mostrar el menú hamburguesa)
@Composable
fun InicioScreen(navController: NavController, username: String) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Estado del menú lateral
    val scope = rememberCoroutineScope() // Alcance para abrir y cerrar el menú lateral

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                // Este es el contenido del menú lateral
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(16.dp)
                ) {
                    Text("Menú", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
                    Text("Opción del Menú 1", modifier = Modifier.padding(8.dp))
                    Text("Opción del Menú 2", modifier = Modifier.padding(8.dp))
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    TextButton(onClick = {
                        scope.launch { drawerState.close() }
                        // Regresamos al login y limpiamos el historial de navegación
                        navController.navigate("login") {
                            popUpTo(0) // Limpia toda la pila de navegación
                        }
                    }) {
                        Text("Cerrar Sesión")
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {

                TopAppBar(
                    title = { Text("") },

                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close() // Abre o cierra el menú lateral
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú Hamburguesa",
                                modifier = Modifier.size(50.dp) // Tamaño del icono
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors( // Colores del TopAppBar
                        containerColor = Color(0xFF333333),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    modifier = Modifier
                        .height(120.dp)
                )
            }
        ) { paddingValues -> // Los elementos debajo del TopAppBar
            // Este es el contenido principal de la pantalla
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                
            }
        }
    }
}
