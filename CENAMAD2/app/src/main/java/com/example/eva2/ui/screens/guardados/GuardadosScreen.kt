package com.example.eva2.ui.screens.guardados

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

// ui/screens/guardados/GuardadosScreen.kt
// GUARDADOS
@Composable
fun GuardadosScreen(onVerPublicacion: (Int) -> Unit) {
    Pagina("Guardados", "Acceso rápido a contenido.") {
        Tarjeta("Diseño sostenible en madera", "Publicación de ejemplo.", {
            onVerPublicacion(1)
        })
    }
}
