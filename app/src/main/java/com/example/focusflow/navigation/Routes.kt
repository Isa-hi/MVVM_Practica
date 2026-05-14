package com.example.focusflow.navigation

sealed class Routes(val route: String) {

    object Config : Routes("config")

    // Rationale recibe tema + metaTiempo para pasarlo a Session
    object Rationale : Routes("rationale/{tema}/{metaTiempo}") {
        fun createRoute(tema: String, metaTiempo: String) =
            "rationale/${tema.encodeForNav()}/${metaTiempo.encodeForNav()}"
    }

    // Session recibe tema + metaTiempo para inicializar el temporizador
    object Session : Routes("session/{tema}/{metaTiempo}") {
        fun createRoute(tema: String, metaTiempo: String) =
            "session/${tema.encodeForNav()}/${metaTiempo.encodeForNav()}"
    }

    // Results recibe tema + tiempoEstudiado + metaTiempo + pausas para calcular calidad
    object Results : Routes("results/{tema}/{tiempoEstudiado}/{metaTiempo}/{pausas}") {
        fun createRoute(
            tema: String,
            tiempoEstudiado: String,
            metaTiempo: String,
            pausas: String
        ) = "results/${tema.encodeForNav()}/$tiempoEstudiado/$metaTiempo/$pausas"
    }
}

// Evita que espacios o caracteres especiales rompan la URL de navegación
private fun String.encodeForNav() = this.replace(" ", "_").replace("/", "-")
fun String.decodeFromNav() = this.replace("_", " ").replace("-", "/")