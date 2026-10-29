package com.example.eva2.ui.screens.registro

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.repository.DatosDemo
import com.example.eva2.ui.components.*
import com.example.eva2.ui.nav.NavColores

// ui/screens/registro/RegistroScreen.kt
// REGISTRO
@Composable
fun RegistroScreen(onRegistrar: () -> Unit, onVolver: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    Pagina("Crear cuenta", "Completa tus datos.") {
        OutlinedTextField(
            nombre, { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            correo, { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            clave, { clave = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Boton("Registrarme", onRegistrar)
        TextButton(onClick = onVolver) { Text("Volver") }
    }
}
