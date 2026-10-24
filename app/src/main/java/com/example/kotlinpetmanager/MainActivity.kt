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
import java.util.UUID // Sirve para generar IDs únicos
import com.example.kotlinpetmanager.ui.theme.KotlinPetManagerTheme

// 1. CAPA DE DATOS (Model)
data class Paciente(
    val id: String, // Identificador único
    val nombre: String,
    val especie: String, // Sirve también para la raza
    val edad: Int,       // Cambiado a Int como pidió el profe
    val contactoEmergencia: String,
    val esUrgencia: Boolean,
    val tiempoRegistroMillis: Long = System.currentTimeMillis() // Nuestro Plus
)

// 2. CAPA DE LÓGICA Y ESTADO (ViewModel)
class PacienteViewModel {
    // Mantiene la lista de pacientes con encadenamiento seguro (private set)
    var pacientes by mutableStateOf(listOf<Paciente>())
        private set

    // Estado de los campos del formulario
    var nombre by mutableStateOf("")
    var especie by mutableStateOf("")
    var edadTexto by mutableStateOf("") // Lo leemos como texto de la caja
    var contactoEmergencia by mutableStateOf("")
    var esUrgencia by mutableStateOf(false)

    // Función para validar e insertar
    fun agregarPaciente() {
        val edadInt = edadTexto.toIntOrNull() ?: 0 // Convertimos a Int seguro

        if (nombre.isNotBlank() && especie.isNotBlank() && edadTexto.isNotBlank()) {
            val nuevoPaciente = Paciente(
                id = UUID.randomUUID().toString(), // Genera un ID automático
                nombre = nombre,
                especie = especie,
                edad = edadInt,
                contactoEmergencia = contactoEmergencia,
                esUrgencia = esUrgencia
            )
            pacientes = pacientes + nuevoPaciente
            limpiarFormulario()
        }
    }

    // Función para limpiar campos
    fun limpiarFormulario() {
        nombre = ""
        especie = ""
        edadTexto = ""
        contactoEmergencia = ""
        esUrgencia = false
    }
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPetManagerTheme {
                // Instanciamos el ViewModel
                val viewModel = remember { PacienteViewModel() }
                AppKotlinPet(viewModel)
            }
        }
    }
}

// 3. CAPA DE INTERFAZ GRÁFICA (View)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppKotlinPet(viewModel: PacienteViewModel) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    var tiempoActual by remember { mutableStateOf(System.currentTimeMillis()) }

    // Reloj para la sala de espera
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            tiempoActual = System.currentTimeMillis()
        }
    }

    // Uso de Scaffold con TopAppBar
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KotlinPet Manager 🐾", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4285F4),
                    titleContentColor = Color.White
                )
            )
        }
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
            // Pestañas
            TabRow(selectedTabIndex = tabSeleccionada) {
                Tab(
                    selected = tabSeleccionada == 0,
                    onClick = { tabSeleccionada = 0 },
                    text = { Text("📝 Registro") }
                )
                Tab(
                    selected = tabSeleccionada == 1,
                    onClick = { tabSeleccionada = 1 },
                    text = { Text("⏱️ Espera (${viewModel.pacientes.size})") }
                )
            }

            if (tabSeleccionada == 0) {
                FormularioRegistro(viewModel)
            } else {
                PantallaSalaEspera(viewModel.pacientes, tiempoActual)
            }
        }
    }
}

@Composable
fun FormularioRegistro(viewModel: PacienteViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Campos OutlinedTextField
        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.nombre = it },
            label = { Text("Nombre del Paciente") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.especie,
            onValueChange = { viewModel.especie = it },
            label = { Text("Especie o Raza") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.edadTexto,
            onValueChange = { viewModel.edadTexto = it },
            label = { Text("Edad (años)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.contactoEmergencia,
            onValueChange = { viewModel.contactoEmergencia = it },
            label = { Text("Contacto de Emergencia") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
        ) {
            Text("¿Requiere atención inmediata (Urgencia)?", modifier = Modifier.weight(1f))
            Switch(checked = viewModel.esUrgencia, onCheckedChange = { viewModel.esUrgencia = it })
        }

        // Botón de acción
        Button(
            onClick = { viewModel.agregarPaciente() },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Registrar Paciente", fontSize = 16.sp)
        }
    }
}

@Composable
fun PantallaSalaEspera(pacientes: List<Paciente>, tiempoActual: Long) {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp).verticalScroll(rememberScrollState())
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
                    // Ahora mostramos también el ID único y el contacto
                    Text(text = "ID: ${paciente.id.take(8)}", color = Color.LightGray, fontSize = 12.sp)
                    Text(text = "Nombre: ${paciente.nombre}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                    Text(text = "Especie/Raza: ${paciente.especie}", color = Color.White, fontSize = 16.sp)
                    Text(text = "Edad: ${paciente.edad} años", color = Color.White, fontSize = 16.sp)

                    if (paciente.contactoEmergencia.isNotBlank()) {
                        Text(text = "Contacto: ${paciente.contactoEmergencia}", color = Color.White, fontSize = 16.sp)
                    }

                    if (paciente.esUrgencia) {
                        Text("🚨 ¡ATENCIÓN INMEDIATA!", color = Color.White, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    } else {
                        val colorTiempo = if (tiempoRestanteMilis <= 0) Color.Green else Color.Yellow
                        Text("Espera: $tiempoFormateado", color = colorTiempo, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 8.dp))
                    }
                }
            }
        }
    }
}