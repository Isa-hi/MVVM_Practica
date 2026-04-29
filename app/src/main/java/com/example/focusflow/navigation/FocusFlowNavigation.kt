package com.example.focusflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.focusflow.ui.screens.* // Importamos las pantallas

@Composable
fun FocusFlowNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Config.route) {

        composable(Routes.Config.route) {
            ConfigScreen(navController)
        }

        composable(Routes.Rationale.route) { backStackEntry ->
            val tema = backStackEntry.arguments?.getString("tema") ?: "Sin tema"
            RationaleScreen(navController, tema)
        }

        composable(Routes.Session.route) { backStackEntry ->
            val tema = backStackEntry.arguments?.getString("tema") ?: "Sin tema"
            SessionScreen(navController, tema)
        }

        composable(Routes.Results.route) { backStackEntry ->
            val tema = backStackEntry.arguments?.getString("tema") ?: "Sin tema"
            val tiempo = backStackEntry.arguments?.getString("tiempoEstudiado") ?: "0"
            ResultsScreen(navController, tema, tiempo)
        }
    }
}