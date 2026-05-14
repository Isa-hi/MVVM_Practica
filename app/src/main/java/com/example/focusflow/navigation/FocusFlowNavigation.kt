package com.example.focusflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.focusflow.ui.screens.*

@Composable
fun FocusFlowNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.Login.route
    ) {

        composable(Routes.Login.route) {
            LoginScreen(navController)
        }

        composable(Routes.Config.route) {
            ConfigScreen(navController)
        }

        composable(
            route = Routes.Rationale.route,
            arguments = listOf(
                navArgument("tema") { type = NavType.StringType },
                navArgument("metaTiempo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            RationaleScreen(
                navController = navController,
                tema = backStackEntry.arguments?.getString("tema") ?: "",
                metaTiempo = backStackEntry.arguments?.getString("metaTiempo") ?: "25"
            )
        }

        composable(
            route = Routes.Session.route,
            arguments = listOf(
                navArgument("tema") { type = NavType.StringType },
                navArgument("metaTiempo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            SessionScreen(
                navController = navController,
                tema = backStackEntry.arguments?.getString("tema") ?: "",
                metaTiempo = backStackEntry.arguments?.getString("metaTiempo") ?: "25"
            )
        }

        composable(
            route = Routes.Results.route,
            arguments = listOf(
                navArgument("tema") { type = NavType.StringType },
                navArgument("tiempoEstudiado") { type = NavType.StringType },
                navArgument("metaTiempo") { type = NavType.StringType },
                navArgument("pausas") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ResultsScreen(
                navController = navController,
                tema = backStackEntry.arguments?.getString("tema") ?: "",
                tiempoEstudiado = backStackEntry.arguments?.getString("tiempoEstudiado") ?: "0",
                metaTiempo = backStackEntry.arguments?.getString("metaTiempo") ?: "25",
                pausas = backStackEntry.arguments?.getString("pausas") ?: "0"
            )
        }
    }
}