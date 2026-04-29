package com.example.focusflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes

@Composable
fun ResultsScreen(navController: NavController, tema: String, tiempoEstudiado: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Resumen de tu Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(24.dp))

        // Datos Críticos viajados por navegación
        Text("Tema estudiado: $tema", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text("Tiempo total enfocado: $tiempoEstudiado minutos", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))
        Text("Score de Calidad: 85% ⭐️", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)

        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                // Regresar al inicio limpiando el historial
                navController.navigate(Routes.Config.route) {
                    popUpTo(Routes.Config.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Volver al Inicio")
        }
    }
}