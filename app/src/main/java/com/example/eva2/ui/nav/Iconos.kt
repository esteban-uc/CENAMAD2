package com.example.eva2.ui.nav

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.dp

object Iconos {
    val Inicio: ImageVector by lazy { crear("Inicio", "M10,20v-6h4v6h5v-8h3L12,3L2,12h3v8z") }
    val Guardados: ImageVector by lazy { crear("Guardados", "M17,3H7c-1.1,0 -1.99,0.9 -1.99,2L5,21l7,-3 7,3V5c0,-1.1 -0.9,-2 -2,-2z") }
    val Cerrar: ImageVector by lazy { crear("Cerrar", "M19,6.41L17.59,5 12,10.59 6.41,5 5,6.41 10.59,12 5,17.59 6.41,19 12,13.41 17.59,19 19,17.59 13.41,12z") }
    val Compartir: ImageVector by lazy { crear("Compartir", "M12,3l5,5h-3.5v6h-3V8H7zM5,15h2v4h10v-4h2v6H5z") }
    val Buscar: ImageVector by lazy {
        crear(
            "Buscar",
            "M15.5,14h-0.79l-0.28,-0.27C15.41,12.59 16,11.11 16,9.5 16,5.91 13.09,3 9.5,3S3,5.91 3,9.5 5.91,16 9.5,16c1.61,0 3.09,-0.59 4.23,-1.57l0.27,0.28v0.79l5,4.99L20.49,19l-4.99,-5zM9.5,14C7.01,14 5,11.99 5,9.5S7.01,5 9.5,5 14,7.01 14,9.5 11.99,14 9.5,14z"
        )
    }
    val Perfil: ImageVector by lazy {
        crear(
            "Perfil",
            "M12,12c2.21,0 4,-1.79 4,-4s-1.79,-4 -4,-4 -4,1.79 -4,4 1.79,4 4,4zM12,14c-2.67,0 -8,1.34 -8,4v2h16v-2c0,-2.66 -5.33,-4 -8,-4z"
        )
    }
    val Menu: ImageVector by lazy { crear("Menu", "M3,18h18v-2H3v2zM3,13h18v-2H3v2zM3,6v2h18V6H3z") }
    val Volver: ImageVector by lazy { crear("Volver", "M20,11H7.83l5.59,-5.59L12,4l-8,8 8,8 1.41,-1.41L7.83,13H20v-2z") }
    val Indicadores: ImageVector by lazy { crear("Indicadores", "M4,20V10h4v10zM10,20V4h4v16zM16,20v-7h4v7z") }
    val CerrarSesion: ImageVector by lazy { crear("CerrarSesion", "M10,17l5,-5l-5,-5v3.5H3v3h7zM13,3h8v18h-8v-2h6V5h-6z") }

    // Convierte un trazo SVG en un ícono de Compose.
    private fun crear(nombre: String, trazo: String): ImageVector =
        ImageVector.Builder(
            name = nombre,
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).addPath(
            pathData = PathParser().parsePathString(trazo).toNodes(),
            fill = SolidColor(Color.Black)
        ).build()
}
