package com.example.focusflow.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CameraAlt
import androidx.compose.material.icons.rounded.Pause
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Stop
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SessionScreen(navController: NavController, tema: String) {

    // ── Estado del temporizador ───────────────────────────────
    val duracionInicialSeg = 45 * 60               // 45 minutos por defecto
    var segundosRestantes by remember { mutableStateOf(duracionInicialSeg) }
    var isRunning by remember { mutableStateOf(true) }
    var showInterventionDialog by remember { mutableStateOf(false) }

    // ── Temporizador en tiempo real ───────────────────────────
    LaunchedEffect(isRunning) {
        while (isRunning && segundosRestantes > 0) {
            delay(1000L)
            segundosRestantes--
        }
        // Si llegó a cero, detener
        if (segundosRestantes == 0) isRunning = false
    }

    // ── Animación del anillo de progreso ─────────────────────
    val progreso = segundosRestantes.toFloat() / duracionInicialSeg.toFloat()
    val progresoAnimado by animateFloatAsState(
        targetValue = progreso,
        animationSpec = tween(durationMillis = 800, easing = LinearEasing),
        label = "progreso"
    )

    val tiempoEstudiado = ((duracionInicialSeg - segundosRestantes) / 60)

    // ── Formato mm:ss ─────────────────────────────────────────
    val minutos = segundosRestantes / 60
    val segundos = segundosRestantes % 60
    val tiempoFormateado = "%02d:%02d".format(minutos, segundos)

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.25f)
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            // ── Encabezado ────────────────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 56.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(if (isRunning) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline)
                    )
                    Text(
                        text = if (isRunning) "Sesión activa" else "Pausado",
                        style = MaterialTheme.typography.labelMedium,
                        color = if (isRunning) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.outline
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = tema,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.CameraAlt,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                        modifier = Modifier.size(14.dp)
                    )
                    Text(
                        text = "Monitoreo activo",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f)
                    )
                }
            }

            // ── Reloj circular con temporizador ───────────────
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.size(260.dp)
            ) {
                // Anillo de fondo
                CircularProgressIndicator(
                    progress = { 1f },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    strokeWidth = 10.dp,
                    strokeCap = StrokeCap.Round
                )
                // Anillo de progreso real
                CircularProgressIndicator(
                    progress = { progresoAnimado },
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.primary,
                    strokeWidth = 10.dp,
                    strokeCap = StrokeCap.Round
                )
                // Texto central
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = tiempoFormateado,
                        style = MaterialTheme.typography.displayMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "restantes",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
                    )
                }
            }

            // ── Controles y acciones ──────────────────────────
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(bottom = 48.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                // Fila de acciones secundarias
                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Botón Pausar / Reanudar
                    OutlinedButton(
                        onClick = { isRunning = !isRunning },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.primary
                        ),
                        border = ButtonDefaults.outlinedButtonBorder
                    ) {
                        Icon(
                            imageVector = if (isRunning) Icons.Rounded.Pause else Icons.Rounded.PlayArrow,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = if (isRunning) "Pausar" else "Reanudar",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }

                    // Botón Simular cansancio
                    OutlinedButton(
                        onClick = { showInterventionDialog = true },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("🧘 Detectar", style = MaterialTheme.typography.labelLarge)
                    }
                }

                // Botón Terminar sesión
                Button(
                    onClick = {
                        isRunning = false
                        navController.navigate(
                            Routes.Results.createRoute(tema, tiempoEstudiado.toString())
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Stop,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Terminar Sesión", style = MaterialTheme.typography.labelLarge)
                }
            }
        }

        // ── Diálogo de intervención ───────────────────────────
        if (showInterventionDialog) {
            AlertDialog(
                onDismissRequest = { showInterventionDialog = false },
                icon = {
                    Text("🧘", style = MaterialTheme.typography.headlineMedium)
                },
                title = {
                    Text(
                        text = "Pareces cansado",
                        style = MaterialTheme.typography.headlineSmall,
                        textAlign = TextAlign.Center
                    )
                },
                text = {
                    Text(
                        text = "¿Quieres dedicar 5 minutos más o prefieres pausar ahora para estirarte?",
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                },
                confirmButton = {
                    Button(
                        onClick = {
                            showInterventionDialog = false
                            isRunning = false
                            navController.navigate(
                                Routes.Results.createRoute(tema, tiempoEstudiado.toString())
                            )
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.error
                        ),
                        shape = RoundedCornerShape(12.dp)
                    ) { Text("Pausar ahora") }
                },
                dismissButton = {
                    TextButton(onClick = { showInterventionDialog = false }) {
                        Text("5 minutos más")
                    }
                },
                shape = RoundedCornerShape(24.dp)
            )
        }
    }
}