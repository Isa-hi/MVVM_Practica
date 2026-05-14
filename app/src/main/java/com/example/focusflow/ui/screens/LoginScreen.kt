package com.example.focusflow.ui.screens

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes

@Composable
fun LoginScreen(navController: NavController) {

    val context = LocalContext.current
    val activity = context as FragmentActivity
    val prefs = context.getSharedPreferences("focusflow_prefs", Context.MODE_PRIVATE)

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }
    var showEnableBiometricDialog by remember { mutableStateOf(false) }

    val biometricEnabled = prefs.getBoolean("biometric_enabled", false)

    val defaultEmail = "david.zacariasv@alumno.buap.mx"
    val defaultPassword = "12345678"

    fun goToHome() {
        navController.navigate(Routes.Config.route) {
            popUpTo(Routes.Login.route) { inclusive = true }
        }
    }

    fun authenticateWithBiometric() {
        val executor = ContextCompat.getMainExecutor(context)

        val biometricPrompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult
                ) {
                    super.onAuthenticationSucceeded(result)
                    goToHome()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Acceso con huella")
            .setSubtitle("Usa tu huella digital")
            .setNegativeButtonText("Cancelar")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }

    val biometricAvailable = BiometricManager.from(context)
        .canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK) ==
            BiometricManager.BIOMETRIC_SUCCESS

    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "FocusFlow",
                style = MaterialTheme.typography.headlineLarge
            )

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = {
                    if (email == defaultEmail && password == defaultPassword) {
                        errorMessage = ""

                        if (!biometricEnabled && biometricAvailable) {
                            showEnableBiometricDialog = true
                        } else {
                            goToHome()
                        }
                    } else {
                        errorMessage = "Correo o contraseña incorrectos"
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Iniciar sesión")
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (biometricEnabled && biometricAvailable) {
                OutlinedButton(
                    onClick = { authenticateWithBiometric() },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Entrar con huella")
                }
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }

    if (showEnableBiometricDialog) {
        AlertDialog(
            onDismissRequest = {},
            title = { Text("Activar huella") },
            text = { Text("¿Deseas habilitar inicio de sesión con huella?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        prefs.edit().putBoolean("biometric_enabled", true).apply()
                        showEnableBiometricDialog = false
                        goToHome()
                    }
                ) {
                    Text("Sí")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        showEnableBiometricDialog = false
                        goToHome()
                    }
                ) {
                    Text("No")
                }
            }
        )
    }
}