package com.example.app_vidasalud_grupo11.ui.screens

import android.content.Context
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SeguridadScreen(navController: NavController, username: String) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val navigateToScreen = { route: String ->
        scope.launch {
            drawerState.close()
            navController.navigate("$route/$username")
        }
    }

    // --- logica biometrica
    val context = LocalContext.current // Obtener el contexto actual
    val activity = context as? FragmentActivity // Convertir el contexto en una actividad

    var authStatus by remember { mutableStateOf("Presiona el botón para verificar.") }

    // Comprobar si el hardware biométrico está disponible
    val biometricManager = BiometricManager.from(context)
    val canAuthenticate = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS

    // Crear el prompt solo si es posible
    val biometricPrompt = remember(activity, canAuthenticate) {
        if (activity == null || !canAuthenticate) {
            null
        } else {
            val executor = ContextCompat.getMainExecutor(context)
            BiometricPrompt(
                activity,
                executor,
                object : BiometricPrompt.AuthenticationCallback() {
                    override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                        super.onAuthenticationError(errorCode, errString)
                        if (errorCode != BiometricPrompt.ERROR_USER_CANCELED) {
                            authStatus = "Error de autenticación: $errString"
                            Toast.makeText(context, authStatus, Toast.LENGTH_SHORT).show()
                        }
                    }

                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                        super.onAuthenticationSucceeded(result)
                        authStatus = "¡Autenticación exitosa!"
                        Toast.makeText(context, authStatus, Toast.LENGTH_SHORT).show()
                    }

                    override fun onAuthenticationFailed() {
                        super.onAuthenticationFailed()
                        authStatus = "Autenticación fallida. Huella no reconocida."
                        Toast.makeText(context, authStatus, Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }

    val promptInfo = remember { // prepara la informacion del prompt
        BiometricPrompt.PromptInfo.Builder()
            .setTitle("Verificación de Seguridad")
            .setSubtitle("Confirma tu identidad usando tu huella dactilar")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    // --- fin logica biometrica


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
                    title = { Text("Seguridad") },
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
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = Color(0xFF333333),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Para proteger tu cuenta, puedes verificar tu identidad usando la huella dactilar.",
                            color = Color.White, textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Button(
                            onClick = { biometricPrompt?.authenticate(promptInfo) },
                            enabled = canAuthenticate // El botón solo se activa si la huella está disponible
                        ) {
                            Text("Verificar con Huella Dactilar")
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                        if (!canAuthenticate) {
                            Text(text = "La autenticación biométrica no está disponible o configurada en este dispositivo.", color = Color.Yellow, style = MaterialTheme.typography.labelSmall, textAlign = TextAlign.Center)
                        } else {
                            Text(text = authStatus, color = Color.White, style = MaterialTheme.typography.labelSmall)
                        }
                    }
                }
            }
        }
    }
}