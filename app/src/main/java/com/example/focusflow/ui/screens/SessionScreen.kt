package com.example.focusflow.ui.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes

@Composable
fun SessionScreen(navController: NavController, tema: String) {
    // Simuladores de estado
    var showInterventionDialog by remember { mutableStateOf(false) }
    var tiempoEstudiadoMock by remember { mutableStateOf(45) } // Minutos simulados

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sesión Activa: $tema", style = MaterialTheme.typography.headlineMedium)
        Text("📷 Cámara activa (Monitoreando enfoque...)", color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(32.dp))
        Text("Temporizador: 45:00", style = MaterialTheme.typography.displayMedium)

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para simular que el sistema detectó frustración
        Button(onClick = { showInterventionDialog = true }) {
            Text("Simular Detección de Frustración")
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Botón para finalizar sesión manualmente y pasar los datos críticos
        Button(
            onClick = {
                navController.navigate(Routes.Results.createRoute(tema, tiempoEstudiadoMock.toString()))
            },
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)
        ) {
            Text("Terminar Sesión")
        }
    }

    // Overlay / Pop-up de Intervención
    if (showInterventionDialog) {
        AlertDialog(
            onDismissRequest = { showInterventionDialog = false },
            title = { Text("Pareces cansado 🧘") },
            text = { Text("¿Quieres dedicar unos últimos 5 minutos o deseas pausar la sesión ahora para estirarte?") },
            confirmButton = {
                TextButton(onClick = {
                    showInterventionDialog = false
                    // Termina la sesión directamente por cansancio
                    navController.navigate(Routes.Results.createRoute(tema, tiempoEstudiadoMock.toString()))
                }) {
                    Text("Pausar ahora")
                }
            },
            dismissButton = {
                TextButton(onClick = { showInterventionDialog = false }) {
                    Text("5 minutos más")
                }
            }
        )
    }
}