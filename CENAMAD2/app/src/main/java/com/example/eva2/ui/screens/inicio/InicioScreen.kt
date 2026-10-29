package com.example.eva2.ui.screens.inicio

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

// ui/screens/inicio/InicioScreen.kt
// PANTALLA PRINCIPAL
@Composable
fun InicioScreen(onExplorar: () -> Unit, onVerIndicadores: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(NavColores.BurdeoOscuro)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        Text("CENAMAD", color = NavColores.Blanco, fontSize = 14.sp)
        Text(
            "Innovación que transforma la madera.",
            color = NavColores.Blanco,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Investigación, tecnología y sostenibilidad para el futuro.",
            color = NavColores.Blanco
        )
        Button(
            onClick = onExplorar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = NavColores.Blanco,
                contentColor = NavColores.Burdeo
            )
        ) { Text("Explorar contenido") }
        OutlinedButton(
            onClick = onVerIndicadores,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Ver indicadores") }
    }
}
