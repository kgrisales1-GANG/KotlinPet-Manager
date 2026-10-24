package com.example.kotlinpetmanager

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

// 2. CAPA DE LÓGICA Y ESTADO (ViewModel)
class PacienteViewModel {
    // Lista de pacientes con encadenamiento seguro (private set)
    var pacientes by mutableStateOf(listOf<Paciente>())
        private set

    // Estado del formulario
    var nombre by mutableStateOf("")
    var especie by mutableStateOf("Canino 🐶")
    var raza by mutableStateOf("")
    var edadTexto by mutableStateOf("")
    var tipoConsulta by mutableStateOf("Consulta General")
    var contactoEmergencia by mutableStateOf("")
    var esUrgencia by mutableStateOf(false)

    fun agregarPaciente() {
        val edadInt = edadTexto.toIntOrNull() ?: 0

        if (nombre.isNotBlank() && raza.isNotBlank() && edadTexto.isNotBlank()) {
            val nuevoPaciente = Paciente(
                nombre = nombre,
                especie = especie,
                raza = raza,
                edad = edadInt,
                tipoConsulta = tipoConsulta,
                contactoEmergencia = contactoEmergencia,
                esUrgencia = esUrgencia
            )
            pacientes = pacientes + nuevoPaciente
            limpiarFormulario()
        }
    }

    fun limpiarFormulario() {
        nombre = ""
        especie = "Canino 🐶"
        raza = ""
        edadTexto = ""
        tipoConsulta = "Consulta General"
        contactoEmergencia = ""
        esUrgencia = false
    }
}