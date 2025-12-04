package com.example.app_vidasalud_grupo11.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.app_vidasalud_grupo11.model.User
import com.example.app_vidasalud_grupo11.viewmodel.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController, loginViewModel: LoginViewModel) {
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Obtenemos el scope para ejecutar corrutinas (llamadas a internet)
    val scope = rememberCoroutineScope()
    // Estado para mostrar mensajes de error o éxito
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(160.dp)
                    .background(Color(0xFF333333)), // Bootstrap black
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "VidaSalud",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(top = 40.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Registro",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold)
                )

                Spacer(modifier = Modifier.height(24.dp))

                OutlinedTextField(
                    value = username,
                    onValueChange = { username = it },
                    label = { Text("Nuevo usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Correo electrónico") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Nueva Contraseña") },
                    singleLine = true,
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = {
                        scope.launch {
                            // --- INICIO DE VALIDACIONES ---

                            // 1. Validar campos vacíos
                            if (username.isBlank() || email.isBlank() || password.isBlank()) {
                                snackbarHostState.showSnackbar("Por favor, completa todos los campos.")
                                return@launch
                            }

                            // 2. Validar formato de correo (que tenga @, dominio, etc.)
                            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                                snackbarHostState.showSnackbar("El formato del correo electrónico no es válido.")
                                return@launch
                            }

                            // --- FIN DE VALIDACIONES ---

                            // Si pasa las validaciones, procedemos al registro
                            val user = User(id = null, username = username, email = email, password = password)

                            val success = loginViewModel.register(user)

                            if (success) {
                                snackbarHostState.showSnackbar("¡Registro exitoso! Iniciando sesión...")
                                navController.navigate("login")
                            } else {
                                snackbarHostState.showSnackbar("Error al registrar. Intente nuevamente.")
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF333333),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Registrarse")
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        navController.navigate("login")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF333333),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Volver atrás")
                }
            }
        }
    }
}