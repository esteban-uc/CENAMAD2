package com.example.eva2.ui.screens.linea

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

// ui/screens/linea/LineaDetalleScreen.kt
// DETALLE DE UNA LÍNEA
@Composable
fun LineaDetalleScreen(
    lineaId: Int,
    onVerInvestigador: (Int) -> Unit,
    onVerProyecto: (Int) -> Unit,
    onVerPublicacion: (Int) -> Unit
) {
    val i = (lineaId - 1).coerceIn(0, DatosDemo.lineas.lastIndex)

    Pagina(DatosDemo.lineas[i], "Detalle de la línea de investigación.") {
        Tarjeta(DatosDemo.investigadores[i], "Investigador relacionado.", {
            onVerInvestigador(i + 1)
        })
        Tarjeta(DatosDemo.proyectos[i], "Proyecto relacionado.", {
            onVerProyecto(i + 1)
        })
        Tarjeta(DatosDemo.publicaciones[i], "Publicación relacionada.", {
            onVerPublicacion(i + 1)
        })
    }
}
