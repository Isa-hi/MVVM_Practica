// ─── FocusFlowNavigation.kt  (actualiza tu archivo existente) ───────────────
// Solo se muestran las partes que cambian. El resto de tu archivo queda igual.

package com.example.focusflow.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.focusflow.ui.screens.*

@Composable
fun FocusFlowNavigation() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Routes.Config.route) {

        // ── Config: sin parámetros ────────────────────────────────────
        composable(Routes.Config.route) {
            ConfigScreen(navController = navController)
        }

        // ── Rationale: recibe tema + metaTiempo ───────────────────────
        composable(
            route = Routes.Rationale.route,                // "rationale/{tema}/{metaTiempo}"
            arguments = listOf(
                navArgument("tema")       { type = NavType.StringType },
                navArgument("metaTiempo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            RationaleScreen(
                navController = navController,
                tema          = backStackEntry.arguments?.getString("tema") ?: "",
                metaTiempo    = backStackEntry.arguments?.getString("metaTiempo") ?: "25"
            )
        }

        // ── Session: recibe tema + metaTiempo ────────────────────────
        composable(
            route = Routes.Session.route,                  // "session/{tema}/{metaTiempo}"
            arguments = listOf(
                navArgument("tema")       { type = NavType.StringType },
                navArgument("metaTiempo") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            SessionScreen(
                navController = navController,
                tema          = backStackEntry.arguments?.getString("tema") ?: "",
                metaTiempo    = backStackEntry.arguments?.getString("metaTiempo") ?: "25"
            )
        }

        // ── Results: recibe tema + tiempoEstudiado + metaTiempo + pausas
        composable(
            route = Routes.Results.route, // "results/{tema}/{tiempoEstudiado}/{metaTiempo}/{pausas}"
            arguments = listOf(
                navArgument("tema")            { type = NavType.StringType },
                navArgument("tiempoEstudiado") { type = NavType.StringType },
                navArgument("metaTiempo")      { type = NavType.StringType },
                navArgument("pausas")          { type = NavType.StringType }
            )
        ) { backStackEntry ->
            ResultsScreen(
                navController   = navController,
                tema            = backStackEntry.arguments?.getString("tema") ?: "",
                tiempoEstudiado = backStackEntry.arguments?.getString("tiempoEstudiado") ?: "0",
                metaTiempo      = backStackEntry.arguments?.getString("metaTiempo") ?: "25",
                pausas          = backStackEntry.arguments?.getString("pausas") ?: "0"
            )
        }
    }
}