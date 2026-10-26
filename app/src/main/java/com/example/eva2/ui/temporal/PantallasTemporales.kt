package com.example.eva2.ui.temporal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.NavColores

/*
 * ============================================================================
 * PANTALLAS TEMPORALES (solo para probar la navegación)
 * Ubicación: ui/temporal/PantallasTemporales.kt
 * ----------------------------------------------------------------------------
 * Son pantallas de mentira. Cuando tu compañero tenga las reales:
 *   1) deben recibir los MISMOS parámetros que las de abajo,
 *   2) si las pone en ui/ (mismo paquete que Navegacion.kt) Kotlin usa las
 *      suyas automáticamente; si las pone en otro paquete, cambia el import
 *      "com.example.eva2.ui.temporal.*" en ui/Navegacion.kt,
 *   3) cuando no quede ninguna, se borra la carpeta "temporal".
 * ============================================================================
 */

// ---------- Piezas reutilizables ----------

@Composable
private fun BotonBurdeo(texto: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = NavColores.Burdeo,
            contentColor = NavColores.Blanco
        )
    ) { Text(texto) }
}

@Composable
private fun PantallaTemporal(
    titulo: String,
    descripcion: String,
    acciones: List<Pair<String, () -> Unit>> = emptyList()
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = titulo,
            color = NavColores.Burdeo,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = 26.sp
        )
        Text(descripcion)
        acciones.forEach { (texto, onClick) -> BotonBurdeo(texto, onClick) }
    }
}

// ===========================================================================
// ACCESO
// ===========================================================================

/** Recibe: onIngresar, onIrARegistro. */
@Composable
fun LoginScreen(onIngresar: () -> Unit, onIrARegistro: () -> Unit) = PantallaTemporal(
    titulo = "Iniciar sesión",
    descripcion = "Pantalla temporal: aquí irá el formulario validado del login.",
    acciones = listOf("Ingresar" to onIngresar, "Crear cuenta" to onIrARegistro)
)

/** Recibe: onRegistrar, onVolver. */
@Composable
fun RegistroScreen(onRegistrar: () -> Unit, onVolver: () -> Unit) = PantallaTemporal(
    titulo = "Crear cuenta",
    descripcion = "Pantalla temporal: aquí irá el formulario de registro con validaciones.",
    acciones = listOf("Registrarme" to onRegistrar, "Volver al login" to onVolver)
)

// ===========================================================================
// SECCIONES (pestañas de arriba)
// ===========================================================================

/** Portada. Recibe: onExplorar, onVerIndicadores. (Imita el hero del mockup.) */
@Composable
fun InicioScreen(onExplorar: () -> Unit, onVerIndicadores: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(listOf(NavColores.Burdeo, NavColores.BurdeoOscuro)))
            .verticalScroll(rememberScrollState())
            .padding(24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "CONOCIMIENTO QUE CONSTRUYE",
            color = NavColores.RosaClaro,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            letterSpacing = 2.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = buildAnnotatedString {
                append("El futuro de la madera, ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic, color = NavColores.RosaClaro)) {
                    append("en tus manos.")
                }
            },
            color = NavColores.Blanco,
            fontFamily = FontFamily.Serif,
            fontSize = 38.sp,
            lineHeight = 44.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Investigación, innovación y conocimiento para transformar la industria de la madera en Chile.",
            color = NavColores.Blanco
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = onExplorar,
            colors = ButtonDefaults.buttonColors(
                containerColor = NavColores.Blanco,
                contentColor = NavColores.Burdeo
            )
        ) { Text("Explorar biblioteca  →") }
        TextButton(onClick = onVerIndicadores) {
            Text("Ver indicadores", color = NavColores.Blanco)
        }
    }
}

/** Investigación. Recibe: onVerLinea(id). */
@Composable
fun InvestigacionScreen(onVerLinea: (Int) -> Unit) = PantallaTemporal(
    titulo = "Investigación",
    descripcion = "Pantalla temporal: aquí irá la lista de líneas y sublíneas de investigación.",
    acciones = listOf(
        "Línea 1" to { onVerLinea(1) },
        "Línea 2" to { onVerLinea(2) },
        "Línea 3" to { onVerLinea(3) }
    )
)

/** CENAMAD. No recibe parámetros. */
@Composable
fun CenamadScreen() = PantallaTemporal(
    titulo = "CENAMAD",
    descripcion = "Pantalla temporal: aquí irá la información del centro."
)

/** Novedades. No recibe parámetros. */
@Composable
fun NovedadesScreen() = PantallaTemporal(
    titulo = "Novedades",
    descripcion = "Pantalla temporal: aquí irán las noticias y eventos."
)

// ===========================================================================
// OTRAS PANTALLAS DE LA BARRA INFERIOR
// ===========================================================================

/** Explorar. Recibe: onVerPublicacion(id), onVerInvestigador(id), onVerProyecto(id). */
@Composable
fun ExplorarScreen(
    onVerPublicacion: (Int) -> Unit,
    onVerInvestigador: (Int) -> Unit,
    onVerProyecto: (Int) -> Unit
) = PantallaTemporal(
    titulo = "Explorar",
    descripcion = "Pantalla temporal: aquí irán el buscador, los filtros y la lista de publicaciones.",
    acciones = listOf(
        "Publicación 1 (abre Vista previa)" to { onVerPublicacion(1) },
        "Publicación 2" to { onVerPublicacion(2) },
        "Investigador 1" to { onVerInvestigador(1) },
        "Proyecto 1" to { onVerProyecto(1) }
    )
)

/** Guardados. Recibe: onVerPublicacion(id). */
@Composable
fun GuardadosScreen(onVerPublicacion: (Int) -> Unit) = PantallaTemporal(
    titulo = "Guardados",
    descripcion = "Pantalla temporal: aquí irán las publicaciones guardadas.",
    acciones = listOf("Publicación 1" to { onVerPublicacion(1) })
)

/** Perfil. Recibe: onCerrarSesion. */
@Composable
fun PerfilScreen(onCerrarSesion: () -> Unit) = PantallaTemporal(
    titulo = "Mi perfil",
    descripcion = "Pantalla temporal: aquí irán los datos del usuario.",
    acciones = listOf("Cerrar sesión" to onCerrarSesion)
)

// ===========================================================================
// DETALLES (reciben el id del elemento elegido)
// ===========================================================================

/** Recibe: lineaId, onVerInvestigador(id), onVerProyecto(id), onVerPublicacion(id). */
@Composable
fun LineaDetalleScreen(
    lineaId: Int,
    onVerInvestigador: (Int) -> Unit,
    onVerProyecto: (Int) -> Unit,
    onVerPublicacion: (Int) -> Unit
) = PantallaTemporal(
    titulo = "Línea $lineaId",
    descripcion = "Pantalla temporal: aquí irán los contenidos relacionados de la línea.",
    acciones = listOf(
        "Investigador 1" to { onVerInvestigador(1) },
        "Proyecto 1" to { onVerProyecto(1) },
        "Publicación 1" to { onVerPublicacion(1) }
    )
)

/** Recibe: investigadorId, onVerProyecto(id), onVerPublicacion(id). */
@Composable
fun InvestigadorScreen(
    investigadorId: Int,
    onVerProyecto: (Int) -> Unit,
    onVerPublicacion: (Int) -> Unit
) = PantallaTemporal(
    titulo = "Investigador $investigadorId",
    descripcion = "Pantalla temporal: aquí irá el perfil con sus proyectos y publicaciones.",
    acciones = listOf(
        "Proyecto 1" to { onVerProyecto(1) },
        "Publicación 1" to { onVerPublicacion(1) }
    )
)

/** Recibe: proyectoId, onVerInvestigador(id). */
@Composable
fun ProyectoScreen(proyectoId: Int, onVerInvestigador: (Int) -> Unit) = PantallaTemporal(
    titulo = "Proyecto $proyectoId",
    descripcion = "Pantalla temporal: aquí irá el detalle del proyecto.",
    acciones = listOf("Ver investigador responsable" to { onVerInvestigador(1) })
)

/** Recibe: publicacionId, onVerInvestigador(id). */
@Composable
fun PublicacionScreen(publicacionId: Int, onVerInvestigador: (Int) -> Unit) = PantallaTemporal(
    titulo = "Publicación $publicacionId",
    descripcion = "Pantalla temporal: aquí irá el detalle de la publicación.",
    acciones = listOf("Ver investigador autor" to { onVerInvestigador(1) })
)

/** No recibe parámetros. */
@Composable
fun IndicadoresScreen() = PantallaTemporal(
    titulo = "Indicadores",
    descripcion = "Pantalla temporal: aquí irán los indicadores con gráficos simples."
)
