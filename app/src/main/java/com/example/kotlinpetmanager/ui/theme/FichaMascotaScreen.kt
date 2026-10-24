package com.example.kotlinpetmanager.ui.theme
package com.example.kotlinpetmanager // Mantén tu paquete original

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FichaMascota(modifier: Modifier = Modifier) {
    val nombre = "Firulais"
    val sexo = 'M'
    var edad = 3
    var peso = 12.5f
    var estadoVacunacion = false

    // Simulación: El paciente cumplió años y subió de peso
    edad += 1
    peso += 1.3f
    estadoVacunacion = true

    // Clasificación de la mascota usando la estructura 'when'[cite: 7]
    val esUrgencia = true
    val categoria = when {
        edad in 0..1 -> "Cachorro"
        edad in 2..7 -> "Adulto"
        edad >= 8 -> "Senior"
        else -> "Edad no válida"
    }

    // Operadores lógicos en un 'if' para evaluar la prioridad[cite: 7]
    val atencion = if (esUrgencia || categoria == "Senior") {
        "Prioridad Alta"
    } else {
        "Atención Regular"
    }

    // Interfaz visual actualizada
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "🐾 Ficha del Paciente Actualizada")
        Text(text = "----------------------------------")
        Text(text = "Nombre: $nombre")
        Text(text = "Sexo: $sexo")
        Text(text = "Edad actual: $edad años")
        Text(text = "Peso actual: $peso kg")
        Text(text = "¿Vacunado?: $estadoVacunacion")
        Text(text = "----------------------------------")
        Text(text = "Categoría: $categoria")
        Text(text = "Estado: $atencion")
    }
}
