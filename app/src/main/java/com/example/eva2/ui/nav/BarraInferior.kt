package com.example.eva2.ui.nav

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

data class ItemInferior(
    val ruta: String,
    val titulo: String,
    val icono: ImageVector,
    val rutasQueLaActivan: List<String> = listOf(ruta)
)

val ItemsInferiores = listOf(
    ItemInferior(
        ruta = Rutas.INICIO,
        titulo = "Inicio",
        icono = Iconos.Inicio,
        rutasQueLaActivan = Secciones.map { it.ruta }
    ),
    ItemInferior(Rutas.EXPLORAR, "Explorar", Iconos.Buscar),
    ItemInferior(Rutas.GUARDADOS, "Guardados", Iconos.Guardados),
    ItemInferior(Rutas.PERFIL, "Perfil", Iconos.Perfil)
)

val RutasPrincipales = ItemsInferiores.flatMap { it.rutasQueLaActivan }.distinct()

@Composable
fun BarraInferior(
    rutaActual: String?,
    onItemClick: (String) -> Unit
) {
    Column {
        Divisor()
        NavigationBar(
            containerColor = NavColores.Crema,
            tonalElevation = 0.dp
        ) {
            ItemsInferiores.forEach { item ->
                NavigationBarItem(
                    selected = rutaActual in item.rutasQueLaActivan,
                    onClick = { onItemClick(item.ruta) },
                    icon = { Icon(item.icono, contentDescription = item.titulo) },
                    label = { Text(item.titulo) },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = NavColores.Burdeo,
                        selectedTextColor = NavColores.Burdeo,
                        indicatorColor = NavColores.RosaClaro,
                        unselectedIconColor = NavColores.TextoGris,
                        unselectedTextColor = NavColores.TextoGris
                    )
                )
            }
        }
    }
}
