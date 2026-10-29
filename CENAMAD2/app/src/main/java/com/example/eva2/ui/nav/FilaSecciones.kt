package com.example.eva2.ui.nav

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/*
 * ============================================================================
 * NAVEGACIÓN · FilaSecciones.kt
 * Ubicación: ui/nav/FilaSecciones.kt
 * ----------------------------------------------------------------------------
 * Fila de pestañas de arriba (Portada · Investigación · CENAMAD · Novedades),
 * igual que el mockup. La pestaña activa va en burdeo con una línea abajo
 * (el color cambia con animación).
 *
 *  - Agregar/quitar una sección ........ lista "Secciones" (abajo)
 *  - Cambiar el texto de una pestaña ... campo "titulo"
 *  - Cuándo se muestra esta fila ....... ui/Navegacion.kt (esSeccion)
 * ============================================================================
 */
data class Seccion(val ruta: String, val titulo: String)

val Secciones = listOf(
    Seccion(Rutas.INICIO, "Portada"),
    Seccion(Rutas.INVESTIGACION, "Investigación"),
    Seccion(Rutas.CENAMAD, "CENAMAD"),
    Seccion(Rutas.NOVEDADES, "Novedades")
)

@Composable
fun FilaSecciones(
    rutaActual: String?,
    onSeccion: (String) -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Secciones.forEach { seccion ->
            val activa = rutaActual == seccion.ruta

            // Colores animados al cambiar de sección
            val colorTexto by animateColorAsState(
                targetValue = if (activa) NavColores.Burdeo else NavColores.TextoGris,
                label = "colorTextoSeccion"
            )
            val colorLinea by animateColorAsState(
                targetValue = if (activa) NavColores.Burdeo else Color.Transparent,
                label = "colorLineaSeccion"
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .clickable { onSeccion(seccion.ruta) }
                    .padding(top = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = seccion.titulo,
                    color = colorTexto,
                    fontSize = 12.sp,
                    fontWeight = if (activa) FontWeight.SemiBold else FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(10.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(colorLinea)
                )
            }
        }
    }
}
