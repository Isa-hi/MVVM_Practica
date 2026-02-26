package com.example.mvvm.model

// Creen una clase simulada que devuelva la información requerida por el proceso.
class DataRepository {

    // Función que simula la lectura de la cámara o micrófono (Dummy Data)
    fun getSimulatedSensorData(): Boolean {
        // Para el MVP, simulamos que detectó un bostezo o distracción (devuelve true).
        return true
    }
}