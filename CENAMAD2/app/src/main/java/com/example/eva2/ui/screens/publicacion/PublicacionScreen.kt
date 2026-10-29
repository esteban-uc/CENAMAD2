package com.example.eva2.ui.screens.publicacion

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

// ui/screens/publicacion/PublicacionScreen.kt
// DETALLE DE PUBLICACIÓN
@Composable
fun PublicacionScreen(
    publicacionId: Int,
    onVerInvestigador: (Int) -> Unit
) {
    val i = (publicacionId - 1).coerceIn(0, DatosDemo.publicaciones.lastIndex)

    Pagina(DatosDemo.publicaciones[i], "Publicación de demostración.") {
        Tarjeta("Tipo", "Contenido científico")
        Tarjeta(DatosDemo.investigadores[i], "Autor relacionado.", {
            onVerInvestigador(i + 1)
        })
    }
}
