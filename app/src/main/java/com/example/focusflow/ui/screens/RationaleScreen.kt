package com.example.focusflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes
@Composable
fun RationaleScreen(navController: NavController, tema: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Permisos de Cámara", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Para ayudarte a mantener el enfoque en '$tema', necesitamos acceder a tu cámara. " +
                "Detectaremos si te estás cansando. Todo el procesamiento se hace en tu teléfono; " +
                "no grabamos ni subimos ningún video.")

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Simulamos que aceptó el permiso y vamos a la sesión
                navController.navigate(Routes.Session.createRoute(tema))
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Entendido y Permitir")
        }
    }
}