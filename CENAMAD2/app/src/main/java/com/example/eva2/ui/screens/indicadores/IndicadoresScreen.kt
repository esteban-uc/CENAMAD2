package com.example.eva2.ui.screens.indicadores

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

// ui/screens/indicadores/IndicadoresScreen.kt
// INDICADORES
@Composable
fun IndicadoresScreen() {
    Pagina("Indicadores", "Cifras ficticias para mostrar la interfaz.") {
        Tarjeta("03 · Líneas", "Áreas de investigación.")
        Tarjeta("03 · Investigadores", "Personas de ejemplo.")
        Tarjeta("03 · Proyectos", "Proyectos de ejemplo.")
        Tarjeta("03 · Publicaciones", "Publicaciones de ejemplo.")
    }
}
