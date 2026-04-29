package com.example.focusflow.navigation

sealed class Routes(val route: String) {
    object Config : Routes("config_screen")
    object Rationale : Routes("rationale_screen/{tema}") {
        fun createRoute(tema: String) = "rationale_screen/$tema"
    }
    object Session : Routes("session_screen/{tema}") {
        fun createRoute(tema: String) = "session_screen/$tema"
    }
    object Results : Routes("results_screen/{tema}/{tiempoEstudiado}") {
        fun createRoute(tema: String, tiempoEstudiado: String) = "results_screen/$tema/$tiempoEstudiado"
    }
}