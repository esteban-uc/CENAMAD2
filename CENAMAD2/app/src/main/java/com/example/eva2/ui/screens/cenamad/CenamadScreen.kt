package com.example.eva2.ui.screens.cenamad

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

// ui/screens/cenamad/CenamadScreen.kt
// INFORMACIÓN DE CENAMAD
@Composable
fun CenamadScreen() {
    Pagina("Sobre CENAMAD", "Ciencia para una industria más sostenible.") {
        Tarjeta("Nuestra misión", "Impulsar la investigación y la innovación.")
        Tarjeta("Nuestro trabajo", "Desarrollo tecnológico y formación de capital humano.")
        Tarjeta("Colaboración", "Conectar investigadores, proyectos y publicaciones.")
    }
}
