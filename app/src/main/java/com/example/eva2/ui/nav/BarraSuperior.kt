package com.example.eva2.ui.nav

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


//LOGO
@Composable
fun LogoCenamad() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(CircleShape)
                .background(NavColores.Burdeo),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "C",
                color = NavColores.Blanco,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = "CENAMAD",
            color = NavColores.TextoOscuro,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 3.sp
        )
    }
}

@Composable
fun BarraSuperiorPrincipal(
    rutaActual: String?,
    mostrarSecciones: Boolean,
    onMenu: () -> Unit,
    onSeccion: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavColores.Crema)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(start = 16.dp, end = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            LogoCenamad()
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = onMenu) {
                Icon(
                    imageVector = Iconos.Menu,
                    contentDescription = "Abrir menú",
                    tint = NavColores.TextoOscuro
                )
            }
        }
        AnimatedVisibility(visible = mostrarSecciones) {
            FilaSecciones(rutaActual = rutaActual, onSeccion = onSeccion)
        }

        Divisor()
    }
}

// Barra de detalle
@Composable
fun BarraSuperiorDetalle(
    titulo: String,
    onAtras: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavColores.Crema)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onAtras) {
                Icon(
                    imageVector = Iconos.Volver,
                    contentDescription = "Volver",
                    tint = NavColores.TextoOscuro
                )
            }
            Text(
                text = titulo,
                color = NavColores.TextoOscuro,
                fontFamily = FontFamily.Serif,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp
            )
        }
        Divisor()
    }
}
@Composable
fun BarraSuperiorVistaPrevia(
    titulo: String,
    subtitulo: String? = null,
    onCerrar: () -> Unit,
    onCompartir: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavColores.Crema)
            .statusBarsPadding()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onCerrar) {
                Icon(
                    imageVector = Iconos.Cerrar,
                    contentDescription = "Cerrar",
                    tint = NavColores.TextoOscuro
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = titulo,
                    color = NavColores.TextoOscuro,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 16.sp
                )
                if (subtitulo != null) {
                    Text(
                        text = subtitulo,
                        color = NavColores.TextoGris,
                        fontSize = 12.sp
                    )
                }
            }
            IconButton(onClick = onCompartir) {
                Icon(
                    imageVector = Iconos.Compartir,
                    contentDescription = "Compartir",
                    tint = NavColores.TextoOscuro
                )
            }
        }
        Divisor()
    }
}
@Composable
fun Divisor() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(NavColores.Linea)
    )
}
