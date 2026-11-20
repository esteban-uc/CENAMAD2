package com.example.eva2.ui.nav

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private data class OpcionMenu(val ruta: String, val titulo: String, val icono: ImageVector)

private val opcionesMenu = listOf(
    OpcionMenu(Rutas.INICIO, "Inicio", Iconos.Inicio),
    OpcionMenu(Rutas.INVESTIGACION, "Investigación", Iconos.Buscar),
    OpcionMenu(Rutas.EXPLORAR, "Explorar", Iconos.Buscar),
    OpcionMenu(Rutas.GUARDADOS, "Guardados", Iconos.Guardados),
    OpcionMenu(Rutas.INDICADORES, "Indicadores", Iconos.Indicadores),
    OpcionMenu(Rutas.PERFIL, "Mi perfil", Iconos.Perfil)
)

@Composable
fun ContenidoMenuLateral(
    rutaActual: String?,
    onNavegar: (String) -> Unit,
    onCerrarSesion: () -> Unit
) {
    val colores = NavigationDrawerItemDefaults.colors(
        selectedContainerColor = NavColores.RosaClaro,
        selectedTextColor = NavColores.Burdeo,
        selectedIconColor = NavColores.Burdeo,
        unselectedContainerColor = Color.Transparent,
        unselectedTextColor = NavColores.TextoOscuro,
        unselectedIconColor = NavColores.TextoGris
    )

    ModalDrawerSheet(drawerContainerColor = NavColores.Crema) {

        // Encabezado
        Column(modifier = Modifier.padding(20.dp)) {
            LogoCenamad()
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Centro Nacional de Excelencia para la Industria de la Madera",
                color = NavColores.TextoGris,
                fontSize = 12.sp
            )
        }
        Divisor()
        Spacer(modifier = Modifier.height(8.dp))
        // Opciones nevagacion
        opcionesMenu.forEach { opcion ->
            NavigationDrawerItem(
                label = { Text(opcion.titulo) },
                icon = { Icon(opcion.icono, contentDescription = opcion.titulo) },
                selected = rutaActual == opcion.ruta,
                onClick = { onNavegar(opcion.ruta) },
                modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                colors = colores
            )
        }
        Spacer(modifier = Modifier.weight(1f))
        Divisor()

        // Cerrar sesión
        NavigationDrawerItem(
            label = { Text("Cerrar sesión") },
            icon = { Icon(Iconos.CerrarSesion, contentDescription = "Cerrar sesión") },
            selected = false,
            onClick = onCerrarSesion,
            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
            colors = colores
        )
    }
}
