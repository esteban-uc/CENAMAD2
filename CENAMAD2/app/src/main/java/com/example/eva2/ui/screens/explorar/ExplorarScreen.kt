package com.example.eva2.ui.screens.explorar

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

// ui/screens/explorar/ExplorarScreen.kt
// BUSCADOR Y FILTROS
@Composable
fun ExplorarScreen(
    onVerPublicacion: (Int) -> Unit,
    onVerInvestigador: (Int) -> Unit,
    onVerProyecto: (Int) -> Unit
) {
    var busqueda by remember { mutableStateOf("") }
    var filtro by remember { mutableStateOf("Todo") }
    val filtros = listOf("Todo", "Publicaciones", "Investigadores", "Proyectos")

    Pagina("Explorar", "Encuentra contenido de investigación.") {
        OutlinedTextField(
            busqueda, { busqueda = it },
            label = { Text("Buscar contenido") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        // Los chips permiten seleccionar el tipo de resultado.
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            filtros.take(2).forEach {
                FilterChip(
                    selected = filtro == it,
                    onClick = { filtro = it },
                    label = { Text(it) }
                )
            }
        }
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            filtros.drop(2).forEach {
                FilterChip(
                    selected = filtro == it,
                    onClick = { filtro = it },
                    label = { Text(it) }
                )
            }
        }

        // Buscamos coincidencias en los nombres.
        if (filtro == "Todo" || filtro == "Publicaciones") {
            DatosDemo.publicaciones.forEachIndexed { i, nombre ->
                if (nombre.contains(busqueda, ignoreCase = true)) {
                    Tarjeta(nombre, "Publicación de ejemplo", { onVerPublicacion(i + 1) })
                }
            }
        }

        if (filtro == "Todo" || filtro == "Investigadores") {
            DatosDemo.investigadores.forEachIndexed { i, nombre ->
                if (nombre.contains(busqueda, ignoreCase = true)) {
                    Tarjeta(nombre, "Investigador de ejemplo", { onVerInvestigador(i + 1) })
                }
            }
        }

        if (filtro == "Todo" || filtro == "Proyectos") {
            DatosDemo.proyectos.forEachIndexed { i, nombre ->
                if (nombre.contains(busqueda, ignoreCase = true)) {
                    Tarjeta(nombre, "Proyecto de ejemplo", { onVerProyecto(i + 1) })
                }
            }
        }
    }
}
