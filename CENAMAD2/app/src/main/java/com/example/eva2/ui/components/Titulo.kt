package com.example.eva2.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.NavColores

/** Título + descripción con el estilo de la app. Ubicación: ui/components/Titulo.kt */
@Composable
fun Titulo(texto: String, descripcion: String = "") {
    Text(texto, fontSize = 27.sp, fontWeight = FontWeight.Bold, color = NavColores.Burdeo)
    if (descripcion.isNotEmpty()) {
        Text(descripcion, color = NavColores.TextoGris)
    }
}
