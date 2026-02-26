package com.example.mvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mvvm.model.DataRepository

// Debe heredar de la clase ViewModel nativa de Android.
class MainViewModel : ViewModel() {

    private val repository = DataRepository()

    // Variables observables (MutableLiveData) para almacenar el estado de la pantalla.
    private val _uiState = MutableLiveData<LearningState>()
    val uiState: LiveData<LearningState> get() = _uiState

    init {
        // Estado inicial: Usuario enfocado, tema normal
        _uiState.value = LearningState(isBored = false, message = "Tema 1: Matemáticas Básicas", themeColor = "#FFFFFF")
    }

    // Este método es llamado por la Vista tras una interacción del usuario
    fun simulateBoredomDetection() {
        // Obtenemos el dato simulado del repositorio
        val isUserBored = repository.getSimulatedSensorData()

        if (isUserBored) {
            // Actualizamos el observable, desencadenando la reacción automática de la Vista.
            _uiState.value = LearningState(
                isBored = true,
                message = "¡Detectamos distracción! Cambiando a un video interactivo...",
                themeColor = "#FFDDDD" // Color diferente para el nuevo tema (ej. rojo claro)
            )
        }
    }
}

// Data class para representar el estado de la UI
data class LearningState(
    val isBored: Boolean,
    val message: String,
    val themeColor: String
)