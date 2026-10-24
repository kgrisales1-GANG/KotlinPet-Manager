package com.example.kotlinpetmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily // Importante para la letra
import androidx.compose.ui.unit.dp

@Composable
fun FichaMascota(modifier: Modifier = Modifier) {
    val nombre = "Firulais"
    val sexo = 'M'
    var edad = 3
    var peso = 12.5f
    var estadoVacunacion = false

    edad += 1
    peso += 1.3f
    estadoVacunacion = true

    val esUrgencia = true
    val categoria = when {
        edad in 0..1 -> "Cachorro"
        edad in 2..7 -> "Adulto"
        edad >= 8 -> "Senior"
        else -> "Edad no válida"
    }

    val atencion = if (esUrgencia || categoria == "Senior") {
        "Prioridad Alta"
    } else {
        "Atención Regular"
    }

    // TRAMO 4: Lista inmutable de pacientes en espera[cite: 7]
    val mascotasEnEspera = listOf("Luna", "Max", "Bella", "Rocky")

    // Interfaz visual actualizada con FontFamily.Serif
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "🐾 Ficha del Paciente Actualizada", fontFamily = FontFamily.Serif)
        Text(text = "Nombre: $nombre - Categoría: $categoria", fontFamily = FontFamily.Serif)
        Text(text = "Estado: $atencion", fontFamily = FontFamily.Serif)

        Text(text = "----------------------------------", fontFamily = FontFamily.Serif)

        Text(text = "Pacientes en espera de consulta:", fontFamily = FontFamily.Serif)
        // Recorremos la lista con un ciclo for[cite: 7]
        for (i in mascotasEnEspera.indices) {
            Text(text = "Turno ${i + 1}: ${mascotasEnEspera[i]}", fontFamily = FontFamily.Serif)
        }

        Text(text = "----------------------------------", fontFamily = FontFamily.Serif)

        Text(text = "La clínica abre en:", fontFamily = FontFamily.Serif)
        // Rango numérico regresivo (del 5 al 1)[cite: 7]
        for (minuto in 5 downTo 1) {
            Text(text = "$minuto minutos...", fontFamily = FontFamily.Serif)
        }
    }
}