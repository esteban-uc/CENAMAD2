package com.example.eva2.ui.components

import androidx.compose.foundation.clickable
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

/** Tarjeta blanca con bordes redondeados. Ubicación: ui/components/Tarjeta.kt */
@Composable
fun Tarjeta(
    titulo: String,
    descripcion: String,
    accion: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(if (accion != null) Modifier.clickable { accion() } else Modifier),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = NavColores.Blanco),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(titulo, fontSize = 17.sp, fontWeight = FontWeight.Bold, color = NavColores.Burdeo)
            Spacer(Modifier.height(6.dp))
            Text(descripcion, color = NavColores.TextoGris)
            if (accion != null) {
                Spacer(Modifier.height(8.dp))
                Text("Ver detalle →", color = NavColores.Burdeo)
            }
        }
    }
}
