package com.example.focusflow.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.PauseCircle
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.School
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.focusflow.navigation.Routes
import com.example.focusflow.navigation.decodeFromNav
import kotlin.math.roundToInt

// ─────────────────────────────────────────────────────────────
// Cálculo de calidad de sesión
//
// Fórmula simple pero significativa:
//   puntuaciónBase  = (tiempoEstudiado / metaTiempo) * 100   → qué % de la meta cumpliste
//   penalizaciónPausas = pausas * 5                          → cada pausa resta 5 puntos
//   score = (puntuaciónBase - penalizaciónPausas).coerceIn(0, 100)
//
// Ejemplos:
//   Meta 25 min, estudió 25, 0 pausas  → 100 %
//   Meta 25 min, estudió 20, 2 pausas  → 80 - 10 = 70 %
//   Meta 25 min, estudió 10, 5 pausas  →  40 - 25 = 15 %
// ─────────────────────────────────────────────────────────────
private fun calcularCalidad(tiempoEstudiado: Int, metaTiempo: Int, pausas: Int): Int {
    if (metaTiempo <= 0) return 0
    val porcentajeCompletado = (tiempoEstudiado.toFloat() / metaTiempo.toFloat()) * 100f
    val penalizacion = pausas * 5
    return (porcentajeCompletado - penalizacion).roundToInt().coerceIn(0, 100)
}

private fun mensajeCalidad(score: Int): String = when {
    score >= 90 -> "¡Sesión perfecta! Eres una máquina de productividad. 🔥"
    score >= 75 -> "¡Excelente concentración! Mantén el ritmo."
    score >= 55 -> "Buen trabajo. Con menos pausas llegarás al 100 %."
    score >= 35 -> "Sesión corta, pero algo es algo. ¡Mañana más!"
    else        -> "Empieza pequeño: intenta 10 minutos sin pausas."
}

private fun colorCalidad(score: Int, tertiary: Color, primary: Color, error: Color): Color = when {
    score >= 75 -> tertiary   // verde
    score >= 45 -> primary    // azul
    else        -> error      // rojo
}

@Composable
fun ResultsScreen(
    navController: NavController,
    tema: String,
    tiempoEstudiado: String,
    metaTiempo: String,
    pausas: String
) {
    val temaDecoded       = tema.decodeFromNav()
    val tiempoInt         = tiempoEstudiado.toIntOrNull() ?: 0
    val metaInt           = metaTiempo.toIntOrNull()?.takeIf { it > 0 } ?: 25
    val pausasInt         = pausas.toIntOrNull() ?: 0
    val score             = calcularCalidad(tiempoInt, metaInt, pausasInt)
    val mensaje           = mensajeCalidad(score)
    val scoreColor        = colorCalidad(
        score,
        MaterialTheme.colorScheme.tertiary,
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.error
    )

    val gradient = Brush.verticalGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.35f),
            MaterialTheme.colorScheme.background,
            MaterialTheme.colorScheme.background
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(gradient),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // ── Trofeo / celebración ──────────────────────────
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.colorScheme.primary,
                                MaterialTheme.colorScheme.primaryContainer
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Rounded.EmojiEvents,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(46.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "¡Sesión completada!",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Text(
                text = "Aquí está tu resumen de hoy",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.55f),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ── Tarjeta de estadísticas ───────────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(8.dp)) {
                    StatRow(
                        icon  = Icons.Rounded.School,
                        label = "Tema estudiado",
                        value = temaDecoded
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    )
                    StatRow(
                        icon  = Icons.Rounded.Schedule,
                        label = "Tiempo enfocado / meta",
                        value = "$tiempoInt / $metaInt min"
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    )
                    StatRow(
                        icon  = Icons.Rounded.PauseCircle,
                        label = "Interrupciones",
                        value = if (pausasInt == 0) "Ninguna ✓" else "$pausasInt pausa${if (pausasInt > 1) "s" else ""}"
                    )
                    HorizontalDivider(
                        modifier = Modifier.padding(horizontal = 16.dp),
                        color = MaterialTheme.colorScheme.outline.copy(alpha = 0.25f)
                    )
                    StatRow(
                        icon       = Icons.Rounded.Star,
                        label      = "Score de calidad",
                        value      = "$score %",
                        valueColor = scoreColor
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ── Badge de mensaje motivacional ─────────────────
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = mensaje,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ── Botón volver al inicio ────────────────────────
            Button(
                onClick = {
                    navController.navigate(Routes.Config.route) {
                        popUpTo(Routes.Config.route) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
            ) {
                Icon(
                    imageVector = Icons.Rounded.Home,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Volver al Inicio", style = MaterialTheme.typography.labelLarge)
            }
        }
    }
}

@Composable
private fun StatRow(
    icon: ImageVector,
    label: String,
    value: String,
    valueColor: Color = MaterialTheme.colorScheme.onSurface
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primaryContainer),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(20.dp)
            )
        }
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.55f)
            )
            Text(
                text = value,
                style = MaterialTheme.typography.titleMedium,
                color = valueColor
            )
        }
    }
}