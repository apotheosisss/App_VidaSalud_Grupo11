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
import androidx.navigation.NavController
import com.example.app_vidasalud_grupo11.viewmodel.AlimentacionViewModel
import kotlinx.coroutines.launch
import java.text.DecimalFormat


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlimentacionScreen(
    navController: NavController,
    username: String,
    alimentacionViewModel: AlimentacionViewModel
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Observar el estado desde el ViewModel
    val uiState by alimentacionViewModel.uiState.collectAsState()
    val df = DecimalFormat("#.##")

    // Cálculos para el bloque de estadísticas (promedio)
    val diasAnalizados = uiState.diasContador
    val avgCalorias = if (uiState.caloriasList.isNotEmpty()) uiState.caloriasList.average() else 0.0
    val avgProteinas = if (uiState.proteinasList.isNotEmpty()) uiState.proteinasList.average() else 0.0
    val avgGrasas = if (uiState.grasasList.isNotEmpty()) uiState.grasasList.average() else 0.0

    val navigateToScreen = { route: String -> // Función para navegar a pantallas
        scope.launch { // Lanzar una corrutina para cerrar el menú
            drawerState.close()
            navController.navigate("$route/$username") // Navegar a la pantalla
        }
    }

    ModalNavigationDrawer( // Componente para el menú lateral
        drawerState = drawerState, // Estado del menú lateral
        drawerContent = { // Contenido del menú lateral
            ModalDrawerSheet { // Componente para el contenido del menú lateral
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
        Scaffold( // Componente para la estructura de la pantalla
            topBar = { // Barra superior
                TopAppBar( // Componente para la barra superior
                    title = { Text("Alimentación") },
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
                // input de datos

                InputBlock(label = "Calorías consumidas hoy", value = alimentacionViewModel.caloriasInput, onValueChange = alimentacionViewModel::onCaloriasInputChange)
                Spacer(modifier = Modifier.height(16.dp))
                InputBlock(label = "Proteínas consumidas hoy (gr)", value = alimentacionViewModel.proteinasInput, onValueChange = alimentacionViewModel::onProteinasInputChange)
                Spacer(modifier = Modifier.height(16.dp))
                InputBlock(label = "Grasas consumidas hoy (gr)", value = alimentacionViewModel.grasasInput, onValueChange = alimentacionViewModel::onGrasasInputChange)

                Spacer(modifier = Modifier.height(24.dp))

                // boton para guardar los datos del dia
                Button(onClick = { alimentacionViewModel.addAlimentacionData() }) {
                    Text("Guardar Datos del Día")
                }

                Spacer(modifier = Modifier.height(24.dp))

                // resutlados de los datos

                Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF333333), shape = RoundedCornerShape(16.dp)) {
                    Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Tus Estadísticas de Alimentación", style = MaterialTheme.typography.titleMedium, color = Color.White)
                        Spacer(modifier = Modifier.height(12.dp))
                        Text("Días analizados: $diasAnalizados", color = Color.White)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Promedio de calorías: ${df.format(avgCalorias)} kcal", color = Color.White)
                        Text("Promedio de proteínas: ${df.format(avgProteinas)} gr", color = Color.White)
                        Text("Promedio de grasas: ${df.format(avgGrasas)} gr", color = Color.White)
                    }
                }
            }
        }
    }
}

// Composable reutilizable para los bloques de input
@Composable
private fun InputBlock(label: String, value: String, onValueChange: (String) -> Unit) {
    Surface(modifier = Modifier.fillMaxWidth(), color = Color(0xFF333333), shape = RoundedCornerShape(16.dp)) { // Caja de superficie

        Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) { // Columna para el bloque de input
            OutlinedTextField(
                value = value, // Valor actual del campo de texto
                onValueChange = onValueChange, // Llama a la función de actualización
                label = { Text(label) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                singleLine = true, // Permite solo una línea de texto
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White, unfocusedTextColor = Color.White, cursorColor = Color.White,
                    focusedIndicatorColor = Color.White, unfocusedIndicatorColor = Color.LightGray,
                    focusedLabelColor = Color.White, unfocusedLabelColor = Color.LightGray,
                    unfocusedContainerColor = Color.Transparent, focusedContainerColor = Color.Transparent
                )
            )
        }
    }
}
