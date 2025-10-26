package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun ConsejosScreen(navController: NavController, username: String) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

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
                    title = { Text("Consejos") },
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
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- ESPACIOS PARA CONSEJOS ---
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text("Consejo 1: Bebe suficiente agua durante el día se´gun tu peso.", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text("Consejo 2: Realiza al menos 30 minutos de actividad física al dia.", color = Color.White)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text("Consejo 3: Duerme entre 7 y 8 horas cada noche.", color = Color.White)
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Box(modifier = Modifier.padding(16.dp), contentAlignment = Alignment.Center) {
                        Text("Consejo 4: Recuerda pararte luego de pasar mucho tiempo sentado.", color = Color.White)
                    }
                }
            }
        }
    }
}