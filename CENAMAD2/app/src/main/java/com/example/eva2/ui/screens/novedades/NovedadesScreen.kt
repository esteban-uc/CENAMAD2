package com.example.eva2.ui.screens.novedades

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

// ui/screens/novedades/NovedadesScreen.kt
// NOVEDADES
@Composable
fun NovedadesScreen() {
    Pagina("Novedades", "Noticias y actividades de ejemplo.") {
        Tarjeta("Seminario de madera", "Actividad sobre construcción sostenible.")
        Tarjeta("Nueva publicación", "Material técnico de investigación.")
        Tarjeta("Encuentro científico", "Colaboración entre investigadores.")
    }
}
