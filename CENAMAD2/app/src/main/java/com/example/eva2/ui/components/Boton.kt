package com.example.eva2.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.NavColores

/** Botón principal reutilizable. Ubicación: ui/components/Boton.kt */
@Composable
fun Boton(texto: String, accion: () -> Unit) {
    Button(
        onClick = accion,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = NavColores.Burdeo),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(texto)
    }
}
