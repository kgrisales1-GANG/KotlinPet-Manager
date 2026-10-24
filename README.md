# KotlinPet Manager 🐾

**Estudiante:** Kevin Stiven Grisales Ossa  
**Código:** 1108334705
**Asignatura:** Desarrollo de Aplicaciones Móviles  

---

## 📱 Descripción de la Aplicación
**KotlinPet Manager** es una aplicación móvil para la gestión de pacientes veterinarios en una sala de espera. Fue desarrollada en Android Studio utilizando **Kotlin** y **Jetpack Compose (Material 3)** bajo una arquitectura **MVVM (Model-View-ViewModel)**.

### Características Principales:
- **Registro completo de pacientes:** Captura de Nombre, Especie (menú desplegable), Raza, Edad, Tipo de Consulta, Contacto de Emergencia e Indicador de Urgencia.
- **Sala de Espera Interactiva:** Visualización de pacientes registrados mediante `LazyColumn` y `ElevatedCard`.
- **Temporizador en Tiempo Real:** Cuenta regresiva de espera de 5 minutos para cada paciente.
- **Arquitectura MVVM:** Separación en capas independientes (`Paciente.kt`, `PacienteViewModel.kt`, `MainActivity.kt`).

---

## 🛠️ Tecnologías Utilizadas
- **Lenguaje:** Kotlin
- **Interfaz:** Jetpack Compose (Material 3: `Scaffold`, `TopAppBar`, `LazyColumn`, `ElevatedCard`, `OutlinedTextField`)
- **Control de Estado:** State & ViewModel (`mutableStateOf`)
- **Control de Versiones:** Git & GitHub

---

## 📸 Capturas de Pantalla de la Aplicación
<div align="center">
  <img src="https://github.com/user-attachments/assets/d8d28b21-fc94-4ff2-ad5d-93ba53c7b89d" width="300" alt="Formulario de Registro" />
  <img src="https://github.com/user-attachments/assets/73391b3a-5fed-402e-bf91-330788a93e5b" width="300" alt="Sala de Espera" />
</div>
