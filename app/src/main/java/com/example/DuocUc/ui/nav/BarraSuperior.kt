package com.example.DuocUc.ui.nav

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

/*
 * ============================================================================
 * NAVEGACIÓN · BarraSuperior.kt
 * Ubicación: ui/nav/BarraSuperior.kt
 * ----------------------------------------------------------------------------
 * Contiene 4 piezas:
 *
 *  1) LogoCenamad ............. círculo burdeo con la "C" + texto CENAMAD.
 *                               (Para usar el logo real: reemplazar el Box por
 *                                Image(painterResource(R.drawable.logo), ...))
 *  2) BarraSuperiorPrincipal .. logo + botón hamburguesa (abre el menú lateral)
 *                               + la fila de secciones cuando corresponde.
 *  3) BarraSuperiorDetalle .... flecha "volver" + título (pantallas de detalle).
 *  4) BarraSuperiorVistaPrevia  "X" para cerrar + título centrado + botón
 *                               compartir (pantalla "Vista previa" de una publicación).
 * ============================================================================
 */

// ---------- 1) LOGO ----------
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

// ---------- 2) BARRA PRINCIPAL ----------
/**
 * @param mostrarSecciones true = muestra la fila Portada/Investigación/CENAMAD/Novedades
 * @param onMenu           se toca la hamburguesa
 * @param onSeccion        se toca una sección (recibe su ruta)
 */
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
            .statusBarsPadding()      // deja espacio para la barra de estado del celular
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

        // La fila de secciones aparece/desaparece con animación
        AnimatedVisibility(visible = mostrarSecciones) {
            FilaSecciones(rutaActual = rutaActual, onSeccion = onSeccion)
        }

        Divisor()
    }
}

// ---------- 3) BARRA DE DETALLE ----------
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

// ---------- 4) BARRA DE VISTA PREVIA ----------
/**
 * Barra de la pantalla "Vista previa" (a pantalla completa, sin barra inferior).
 * @param subtitulo texto pequeño bajo el título (ej. "18 páginas · PDF").
 *                  Es un dato de la publicación, así que lo entrega la pantalla real.
 * @param onCerrar     toca la "X"
 * @param onCompartir  toca el botón compartir
 */
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

// Línea fina que separa la barra del contenido
@Composable
fun Divisor() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(NavColores.Linea)
    )
}
