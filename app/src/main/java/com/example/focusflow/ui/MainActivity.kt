package com.example.focusflow.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.focusflow.navigation.FocusFlowNavigation
import com.example.focusflow.ui.theme.FocusFlowTheme // Asegúrate de que el nombre coincida con tu archivo de tema

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // setContent define qué es lo que se va a dibujar en la pantalla
        setContent {
            // Aplicamos el tema visual general de tu app
            FocusFlowTheme {
                // Surface es el "lienzo" base de Material Design
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Llamamos a nuestro enrutador principal, el cual se encargará
                    // de decidir qué pantalla mostrar (empezando por ConfigScreen)
                    FocusFlowNavigation()
                }
            }
        }
    }
}