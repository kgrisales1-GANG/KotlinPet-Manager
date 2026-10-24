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
        Text(text = "----------------------------------", fontFamily = FontFamily.Serif)

        // TRAMO 5.1: Null Safety (Seguridad contra nulos)
        // Declaramos el contacto como nulo usando el signo de interrogación (String?)
        val contactoEmergencia: String? = null

        // Medimos los caracteres de forma segura con '?.'
        val caracteresContacto = contactoEmergencia?.length

        // Usamos el operador Elvis '?:' para asignar un valor si es nulo[cite: 7]
        val contactoFinal = contactoEmergencia ?: "Sin contacto registrado"

        Text(text = "Contacto de emergencia: $contactoFinal", fontFamily = FontFamily.Serif)
        Text(text = "Longitud del dato: $caracteresContacto", fontFamily = FontFamily.Serif)

        Text(text = "----------------------------------", fontFamily = FontFamily.Serif)

        // TRAMO 5.2: Lambdas y Funciones de Orden Superior[cite: 7]
        Text(text = "Precios de servicios (Recargo Nocturno 10%):", fontFamily = FontFamily.Serif)

        // Lista de precios base[cite: 7]
        val preciosBase = listOf(50000.0, 25000.0, 15000.0)

        // Función Lambda que recibe un precio y le suma el 10%[cite: 7]
        val recargoNocturno = { precio: Double -> precio + (precio * 0.10) }

        // Recorremos la lista con forEach y usamos 'it' para representar cada precio[cite: 7]
        preciosBase.forEach {
            Text(text = "Original: $$it -> Ajustado: $${recargoNocturno(it)}", fontFamily = FontFamily.Serif)
        }
    }
}