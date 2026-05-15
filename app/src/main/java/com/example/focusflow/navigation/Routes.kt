package com.example.focusflow.navigation

sealed class Routes(val route: String) {

    object Login : Routes("login")
    object Config : Routes("config")

    object Rationale : Routes("rationale/{tema}/{metaTiempo}") {
        fun createRoute(tema: String, metaTiempo: String) =
            "rationale/${tema.encodeForNav()}/${metaTiempo.encodeForNav()}"
    }

    object Session : Routes("session/{tema}/{metaTiempo}") {
        fun createRoute(tema: String, metaTiempo: String) =
            "session/${tema.encodeForNav()}/${metaTiempo.encodeForNav()}"
    }

    object Results : Routes("results/{tema}/{tiempoEstudiado}/{metaTiempo}/{pausas}") {
        fun createRoute(
            tema: String,
            tiempoEstudiado: String,
            metaTiempo: String,
            pausas: String
        ) = "results/${tema.encodeForNav()}/$tiempoEstudiado/$metaTiempo/$pausas"
    }
}

private fun String.encodeForNav() = this.replace(" ", "_").replace("/", "-")
fun String.decodeFromNav() = this.replace("_", " ").replace("-", "/")