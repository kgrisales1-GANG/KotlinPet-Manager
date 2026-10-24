package com.example.kotlinpetmanager.ui.theme
package com.example.kotlinpetmanager

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FichaMascota(modifier: Modifier = Modifier) {
    // 1. Declaración de variables usando val (inmutables) y var (mutables)
    val nombre: String = "Firulais"
    val sexo: Char = 'M'
    var edad: Int = 3
    var peso: Float = 12.5f
    var estadoVacunacion: Boolean = false

    // 2. Interfaz Visual: Mostramos los datos en pantalla usando interpolación ($)[cite: 6]
    Column(modifier = modifier.padding(16.dp)) {
        Text(text = "🐾 Ficha del Paciente")
        Text(text = "----------------------------------")
        Text(text = "Nombre: $nombre")
        Text(text = "Sexo: $sexo")
        Text(text = "Edad: $edad años")
        Text(text = "Peso: $peso kg")
        Text(text = "¿Vacunado?: $estadoVacunacion")
    }
}
