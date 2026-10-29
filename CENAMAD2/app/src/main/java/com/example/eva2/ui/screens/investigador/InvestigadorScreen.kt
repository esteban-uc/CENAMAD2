package com.example.eva2.ui.screens.investigador

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

// ui/screens/investigador/InvestigadorScreen.kt
// DETALLE DE INVESTIGADOR
@Composable
fun InvestigadorScreen(
    investigadorId: Int,
    onVerProyecto: (Int) -> Unit,
    onVerPublicacion: (Int) -> Unit
) {
    val i = (investigadorId - 1).coerceIn(0, DatosDemo.investigadores.lastIndex)

    Pagina(DatosDemo.investigadores[i], "Investigador de ejemplo.") {
        Tarjeta(DatosDemo.proyectos[i], "Proyecto relacionado.", {
            onVerProyecto(i + 1)
        })
        Tarjeta(DatosDemo.publicaciones[i], "Publicación relacionada.", {
            onVerPublicacion(i + 1)
        })
    }
}
