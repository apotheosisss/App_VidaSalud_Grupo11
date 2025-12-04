package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(navController: NavController, username: String) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Función para navegar y cerrar el menú
    val navigateToScreen = { route: String ->
        scope.launch {
            drawerState.close()
            navController.navigate("$route/$username")
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .padding(16.dp)
                ) {
                    Text("Menú", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))

                    // Opciones del menú como botones
                    Text("Sueño", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("sueno") }.padding(12.dp))
                    Text("Consejos", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("consejos") }.padding(12.dp))
                    Text("Alimentación", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("alimentacion") }.padding(12.dp))
                    Text("Seguridad", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("seguridad") }.padding(12.dp))

                    Divider(modifier = Modifier.padding(vertical = 8.dp))

                    TextButton(onClick = {
                        scope.launch { drawerState.close() }
                        navController.navigate("login") { popUpTo(0) }
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
                    title = { Text("Inicio") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.apply { if (isClosed) open() else close() } } }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Menú Hamburguesa",
                                modifier = Modifier.size(50.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF333333),
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    modifier = Modifier.height(120.dp)
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("Bienvenido, $username", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Presiona en el icono de arriba para abrir el menú", style = MaterialTheme.typography.headlineMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}
