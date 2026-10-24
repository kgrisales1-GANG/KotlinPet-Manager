package com.example.kotlinpetmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import com.example.kotlinpetmanager.ui.theme.KotlinPetManagerTheme

// Actualizamos la estructura para guardar la raza y el tipo de consulta
data class Paciente(
    val nombre: String,
    val especie: String,
    val raza: String,
    val edad: String,
    val tipoConsulta: String,
    val esUrgencia: Boolean,
    val tiempoRegistroMillis: Long = System.currentTimeMillis()
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPetManagerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PantallaPrincipal(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun PantallaPrincipal(modifier: Modifier = Modifier) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    var listaPacientes by remember { mutableStateOf(listOf<Paciente>()) }

    var tiempoActual by remember { mutableStateOf(System.currentTimeMillis()) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            tiempoActual = System.currentTimeMillis()
        }
    }

    Column(modifier = modifier.fillMaxSize()) {
        TabRow(selectedTabIndex = tabSeleccionada) {
            Tab(
                selected = tabSeleccionada == 0,
                onClick = { tabSeleccionada = 0 },
                text = { Text("📝 Registro") }
            )
            Tab(
                selected = tabSeleccionada == 1,
                onClick = { tabSeleccionada = 1 },
                text = { Text("⏱️ Espera (${listaPacientes.size})") }
            )
        }

        if (tabSeleccionada == 0) {
            PantallaRegistroMascotas(
                onRegistrar = { nuevoPaciente ->
                    listaPacientes = listaPacientes + nuevoPaciente
                }
            )
        } else {
            PantallaSalaEspera(
                pacientes = listaPacientes,
                tiempoActual = tiempoActual
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaRegistroMascotas(onRegistrar: (Paciente) -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var raza by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("") }
    var esUrgencia by remember { mutableStateOf(false) }

    // Variables para el menú desplegable de Especie
    val opcionesEspecie = listOf("Canino 🐶", "Felino 🐱", "Conejo 🐰", "Ave 🦜", "Otro")
    var especieSeleccionada by remember { mutableStateOf(opcionesEspecie[0]) }
    var expandidoEspecie by remember { mutableStateOf(false) }

    // Variables para el menú desplegable de Tipo de Consulta
    val opcionesConsulta = listOf("Consulta General", "Vacunación", "Desparasitación", "Control Post-operatorio", "Estética / Peluquería")
    var consultaSeleccionada by remember { mutableStateOf(opcionesConsulta[0]) }
    var expandidoConsulta by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Text(
            text = "Registrar Nuevo Paciente",
            color = Color(0xFF4285F4),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre del Paciente") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Dropdown para Especie
        ExposedDropdownMenuBox(
            expanded = expandidoEspecie,
            onExpandedChange = { expandidoEspecie = !expandidoEspecie },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = especieSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Especie") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoEspecie) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandidoEspecie,
                onDismissRequest = { expandidoEspecie = false }
            ) {
                opcionesEspecie.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            especieSeleccionada = opcion
                            expandidoEspecie = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = raza,
            onValueChange = { raza = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = edad,
            onValueChange = { edad = it },
            label = { Text("Edad (años)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        // Dropdown para Tipo de Consulta
        ExposedDropdownMenuBox(
            expanded = expandidoConsulta,
            onExpandedChange = { expandidoConsulta = !expandidoConsulta },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            OutlinedTextField(
                value = consultaSeleccionada,
                onValueChange = {},
                readOnly = true,
                label = { Text("Tipo de Consulta") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandidoConsulta) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expandidoConsulta,
                onDismissRequest = { expandidoConsulta = false }
            ) {
                opcionesConsulta.forEach { opcion ->
                    DropdownMenuItem(
                        text = { Text(opcion) },
                        onClick = {
                            consultaSeleccionada = opcion
                            expandidoConsulta = false
                        }
                    )
                }
            }
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text(
                text = "¿Requiere atención inmediata (Urgencia)?",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Switch(
                checked = esUrgencia,
                onCheckedChange = { esUrgencia = it }
            )
        }

        Button(
            onClick = {
                if (nombre.isNotBlank() && raza.isNotBlank() && edad.isNotBlank()) {
                    val nuevoPaciente = Paciente(
                        nombre = nombre,
                        especie = especieSeleccionada,
                        raza = raza,
                        edad = edad,
                        tipoConsulta = consultaSeleccionada,
                        esUrgencia = esUrgencia
                    )
                    onRegistrar(nuevoPaciente)

                    // Limpiar campos
                    nombre = ""
                    raza = ""
                    edad = ""
                    esUrgencia = false
                    especieSeleccionada = opcionesEspecie[0]
                    consultaSeleccionada = opcionesConsulta[0]
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Registrar Paciente", fontSize = 16.sp)
        }
    }
}

@Composable
fun PantallaSalaEspera(pacientes: List<Paciente>, tiempoActual: Long) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        if (pacientes.isEmpty()) {
            Text("No hay pacientes en la sala de espera.", fontSize = 16.sp)
        }

        pacientes.forEach { paciente ->
            val tiempoTranscurrido = tiempoActual - paciente.tiempoRegistroMillis
            val tiempoRestanteMilis = maxOf(0L, 300_000L - tiempoTranscurrido)

            val minutos = (tiempoRestanteMilis / 1000) / 60
            val segundos = (tiempoRestanteMilis / 1000) % 60
            val tiempoFormateado = String.format("%02d:%02d", minutos, segundos)

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = if (paciente.esUrgencia) Color(0xFF8B0000) else Color.DarkGray
                ),
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nombre: ${paciente.nombre}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Especie: ${paciente.especie} - Raza: ${paciente.raza}", color = Color.White, fontSize = 16.sp)
                    Text(text = "Edad: ${paciente.edad} años", color = Color.White, fontSize = 16.sp)
                    Text(text = "Motivo: ${paciente.tipoConsulta}", color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))

                    if (paciente.esUrgencia) {
                        Text(
                            text = "🚨 ¡ATENCIÓN INMEDIATA!",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    } else {
                        val colorTiempo = if (tiempoRestanteMilis <= 0) Color.Green else Color.Yellow
                        Text(
                            text = "Tiempo estimado de espera: $tiempoFormateado",
                            color = colorTiempo,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                        if (tiempoRestanteMilis <= 0) {
                            Text(text = "¡Ya puede ingresar al consultorio!", color = Color.Green)
                        }
                    }
                }
            }
        }
    }
}