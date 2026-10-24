package com.example.kotlinpetmanager

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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

// 3. CAPA DE INTERFAZ GRÁFICA (View)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            KotlinPetManagerTheme {
                val viewModel = remember { PacienteViewModel() }
                PantallaPrincipal(viewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(viewModel: PacienteViewModel) {
    var tabSeleccionada by remember { mutableStateOf(0) }
    var tiempoActual by remember { mutableStateOf(System.currentTimeMillis()) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            tiempoActual = System.currentTimeMillis()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("KotlinPet Manager 🐾", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF4285F4),
                    titleContentColor = Color.White
                )
            )
        },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding).fillMaxSize()) {
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioRegistro(viewModel: PacienteViewModel) {
    val opcionesEspecie = listOf("Canino 🐶", "Felino 🐱", "Conejo 🐰", "Ave 🦜", "Otro")
    var expandidoEspecie by remember { mutableStateOf(false) }

    val opcionesConsulta = listOf("Consulta General", "Vacunación", "Desparasitación", "Control Post-operatorio", "Estética / Peluquería")
    var expandidoConsulta by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        OutlinedTextField(
            value = viewModel.nombre,
            onValueChange = { viewModel.nombre = it },
            label = { Text("Nombre del Paciente") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expandidoEspecie,
            onExpandedChange = { expandidoEspecie = !expandidoEspecie },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.especie,
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
                            viewModel.especie = opcion
                            expandidoEspecie = false
                        }
                    )
                }
            }
        }

        OutlinedTextField(
            value = viewModel.raza,
            onValueChange = { viewModel.raza = it },
            label = { Text("Raza") },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = viewModel.edadTexto,
            onValueChange = { viewModel.edadTexto = it },
            label = { Text("Edad (años)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expandidoConsulta,
            onExpandedChange = { expandidoConsulta = !expandidoConsulta },
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp)
        ) {
            OutlinedTextField(
                value = viewModel.tipoConsulta,
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
                            viewModel.tipoConsulta = opcion
                            expandidoConsulta = false
                        }
                    )
                }
            }
        }

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
            Text(
                text = "¿Requiere atención inmediata (Urgencia)?",
                modifier = Modifier.weight(1f),
                fontSize = 16.sp
            )
            Switch(
                checked = viewModel.esUrgencia,
                onCheckedChange = { viewModel.esUrgencia = it }
            )
        }

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
    if (pacientes.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text("No hay pacientes en la sala de espera.", fontSize = 16.sp)
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp)
        ) {
            items(pacientes) { paciente ->
                val tiempoTranscurrido = tiempoActual - paciente.tiempoRegistroMillis
                val tiempoRestanteMilis = maxOf(0L, 300_000L - tiempoTranscurrido)

                val minutos = (tiempoRestanteMilis / 1000) / 60
                val segundos = (tiempoRestanteMilis / 1000) % 60
                val tiempoFormateado = String.format("%02d:%02d", minutos, segundos)

                ElevatedCard(
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = if (paciente.esUrgencia) Color(0xFF8B0000) else Color.DarkGray
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "ID: ${paciente.id.take(8)}", color = Color.LightGray, fontSize = 12.sp)
                        Text(text = "Nombre: ${paciente.nombre}", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Especie: ${paciente.especie} - Raza: ${paciente.raza}", color = Color.White, fontSize = 16.sp)
                        Text(text = "Edad: ${paciente.edad} años", color = Color.White, fontSize = 16.sp)
                        Text(text = "Motivo: ${paciente.tipoConsulta}", color = Color.White, fontSize = 16.sp, modifier = Modifier.padding(top = 4.dp))

                        if (paciente.contactoEmergencia.isNotBlank()) {
                            Text(text = "Contacto: ${paciente.contactoEmergencia}", color = Color.White, fontSize = 16.sp)
                        }

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
                                text = "Espera estimada: $tiempoFormateado",
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
}