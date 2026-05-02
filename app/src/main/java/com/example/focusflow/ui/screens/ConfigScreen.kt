package com.example.focusflow.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes

@Composable
fun ConfigScreen(navController: NavController) {
    var tema by remember { mutableStateOf("") }
    var metaTiempo by remember { mutableStateOf("") }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Configuración de Sesión", style = MaterialTheme.typography.headlineMedium)
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = tema,
            onValueChange = { tema = it },
            label = { Text("¿Qué vas a estudiar?") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = metaTiempo,
            onValueChange = { metaTiempo = it },
            label = { Text("Meta de tiempo (minutos)") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                // Navegamos a la pantalla de justificación pasando el tema
                if (tema.isNotEmpty()) {
                    navController.navigate(Routes.Rationale.createRoute(tema))
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Siguiente")
        }
    }
}