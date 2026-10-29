package com.example.eva2.ui.screens.investigacion

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

// ui/screens/investigacion/InvestigacionScreen.kt
// LÍNEAS DE INVESTIGACIÓN
@Composable
fun InvestigacionScreen(onVerLinea: (Int) -> Unit) {
    Pagina("Investigación", "Conoce nuestras áreas de trabajo.") {
        DatosDemo.lineas.forEachIndexed { i, nombre ->
            Tarjeta(
                nombre,
                "Investigación aplicada y desarrollo sostenible.",
                { onVerLinea(i + 1) }
            )
        }
    }
}
