package com.example.mvvm.ui

import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import com.example.mvvm.R
import com.example.mvvm.viewmodel.MainViewModel

// Mueva su clase MainActivity a este directorio.
class MainActivity : AppCompatActivity() {

    // Instanciar el ViewModel.
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias a los componentes visuales.
        val rootLayout = findViewById<ConstraintLayout>(R.id.rootLayout)
        val contentTextView = findViewById<TextView>(R.id.contentTextView)
        val simulateSensorButton = findViewById<Button>(R.id.simulateSensorButton)

        // El Enlace (Binding): Observar los cambios de datos.
        viewModel.uiState.observe(this, Observer { state ->
            // Reacción automática de la Vista.
            contentTextView.text = state.message
            rootLayout.setBackgroundColor(Color.parseColor(state.themeColor))
        })

        // Ante una interacción del usuario, la Vista debe notificar al ViewModel.
        simulateSensorButton.setOnClickListener {
            // Simula que el dispositivo detectó aburrimiento en lugar de usar la cámara real
            viewModel.simulateBoredomDetection()
        }
    }
}