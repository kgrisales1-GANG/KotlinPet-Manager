package com.example.kotlinpetmanager

import java.util.UUID

// 1. CAPA DE DATOS (Model)
data class Paciente(
    val id: String = UUID.randomUUID().toString(),
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: Int,
    val tipoConsulta: String,
    val contactoEmergencia: String,
    val esUrgencia: Boolean,
    val tiempoRegistroMillis: Long = System.currentTimeMillis()
)
