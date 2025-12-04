package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.app_vidasalud_grupo11.viewmodel.SuennoViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SuennoScreen(
    navController: NavController,
    username: String,
    suennoViewModel: SuennoViewModel // <-- ¡AQUÍ ESTÁ EL CAMBIO CLAVE!
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val uiState by suennoViewModel.uiState.collectAsState()

    val diasAnalizados = uiState.diasContador
    val promedioSuenno = if (uiState.sleepHours.isNotEmpty()) uiState.sleepHours.average().toFloat() else 0f
    val df = DecimalFormat("#.##")

    val recomendacion = when {
        promedioSuenno == 0f -> "Aún no hay datos para darte una recomendación."
        promedioSuenno < 6 -> "Intenta dormir más. ¡Tu salud te lo agradecerá!"
        promedioSuenno < 7 -> "Intenta dormir un poco más. Estás cerca de la meta."
        promedioSuenno <= 9 -> "¡Sigue así! Tu patrón de sueño es muy saludable."
        else -> "¡Excelente! Duermes más de lo necesario, asegúrate de que sea un descanso de calidad."
    }

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
                    modifier = Modifier.fillMaxWidth(0.7f).padding(16.dp)
                ) {
                    Text("Menú", style = MaterialTheme.typography.titleLarge, modifier = Modifier.padding(bottom = 16.dp))
                    Text("Sueño", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("sueno") }.padding(12.dp))
                    Text("Consejos", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("consejos") }.padding(12.dp))
                    Text("Alimentación", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("alimentacion") }.padding(12.dp))
                    Text("Seguridad", modifier = Modifier.fillMaxWidth().clickable { navigateToScreen("seguridad") }.padding(12.dp))
                    Divider(modifier = Modifier.padding(vertical = 8.dp))
                    TextButton(onClick = { scope.launch { drawerState.close() }; navController.navigate("login") { popUpTo(0) } }) {
                        Text("Cerrar Sesión")
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Sueño") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.apply { if (isClosed) open() else close() } } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú Hamburguesa", modifier = Modifier.size(50.dp))
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
                modifier = Modifier.fillMaxSize().padding(paddingValues).padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // --- BLOQUE 1: INPUT DEL USUARIO ---
                Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF333333), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        OutlinedTextField(
                            value = suennoViewModel.horasInput,
                            onValueChange = { suennoViewModel.onHorasInputChange(it) },
                            label = { Text("Horas de sueño de anoche") },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            singleLine = true,
                            colors = TextFieldDefaults.colors(
                                focusedTextColor = Color.White, unfocusedTextColor = Color.White, cursorColor = Color.White,
                                focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                                focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                                unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(onClick = { suennoViewModel.addSleepData() }) {
                            Text("Guardar Horas")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- BLOQUE 2: EXPLICACIÓN (No cambia) ---
                Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF333333), shape = RoundedCornerShape(16.dp)) {
                    Text(
                        text = "Ingresa tus horas de sueño para recopilar datos y ofrecerte recomendaciones personalizadas.",
                        color = Color.White, modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- BLOQUE 3: RESULTADOS ---
                Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF333333), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Tus Estadísticas de Sueño", style = MaterialTheme.typography.titleMedium, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Días analizados: $diasAnalizados", color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Promedio de sueño: ${df.format(promedioSuenno)} horas", color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Recomendación: $recomendacion",
                            color = Color.White, textAlign = TextAlign.Center, style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}