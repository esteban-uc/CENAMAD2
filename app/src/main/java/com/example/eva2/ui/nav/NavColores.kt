package com.example.eva2.ui.nav

import androidx.compose.ui.graphics.Color

/*
 * ============================================================================
 * NAVEGACIÓN · NavColores.kt
 * Ubicación: ui/nav/NavColores.kt
 * ----------------------------------------------------------------------------
 * Paleta de la navegación (rojo burdeo + crema, como el mockup de CENAMAD).
 * Está separada del tema (ui/theme) para no modificarlo.
 * Para cambiar el color de TODA la navegación, edita solo este archivo.
 * ============================================================================
 */
object NavColores {
    val Burdeo = Color(0xFF8A1C2B)        // color principal (activos, botones)
    val BurdeoOscuro = Color(0xFF5E0F1B)  // fondo del degradado
    val Crema = Color(0xFFF7F3EF)         // fondo de las barras
    val Linea = Color(0xFFE3DCD6)         // líneas divisorias
    val TextoOscuro = Color(0xFF2B2B2B)
    val TextoGris = Color(0xFF7A7A7A)
    val RosaClaro = Color(0xFFF3D5D8)     // fondo del ítem seleccionado
    val Blanco = Color(0xFFFFFFFF)
}
