package com.example.eva2.ui.screens.login

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

// ui/screens/login/LoginScreen.kt
// INICIO DE SESIÓN
@Composable
fun LoginScreen(onIngresar: () -> Unit, onIrARegistro: () -> Unit) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    Pagina("Bienvenido a CENAMAD", "Conocimiento e innovación en madera.") {
        OutlinedTextField(
            correo, { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            clave, { clave = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Boton("Ingresar", onIngresar)
        TextButton(onClick = onIrARegistro) {
            Text("Crear cuenta")
        }
    }
}
