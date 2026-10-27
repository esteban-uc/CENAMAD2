
package com.example.eva2.ui.temporal

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.NavColores

// Datos ficticios para mostrar contenido en la aplicación.
private val lineas = listOf(
    "Construcción en madera",
    "Bioproductos y biomateriales",
    "Recursos forestales"
)

private val investigadores = listOf(
    "Camila Rojas",
    "Mateo Fuentes",
    "Sofía Vidal"
)

private val proyectos = listOf(
    "Sistema modular de madera",
    "Paneles de biomateriales",
    "Trazabilidad forestal"
)

private val publicaciones = listOf(
    "Diseño sostenible en madera",
    "Innovación en biomateriales",
    "Gestión responsable del bosque"
)

// Título reutilizable para mantener el mismo estilo.
@Composable
private fun Titulo(texto: String, descripcion: String = "") {
    Text(
        texto,
        fontSize = 27.sp,
        fontWeight = FontWeight.Bold,
        color = NavColores.Burdeo
    )
    if (descripcion.isNotEmpty()) {
        Text(descripcion, color = NavColores.TextoGris)
    }
}

// Tarjeta blanca con bordes redondeados.
@Composable
private fun Tarjeta(
    titulo: String,
    descripcion: String,
    accion: (() -> Unit)? = null
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (accion != null) Modifier.clickable { accion() }
                else Modifier
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = NavColores.Blanco
        ),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                titulo,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = NavColores.Burdeo
            )
            Spacer(Modifier.height(6.dp))
            Text(descripcion, color = NavColores.TextoGris)
            if (accion != null) {
                Spacer(Modifier.height(8.dp))
                Text("Ver detalle →", color = NavColores.Burdeo)
            }
        }
    }
}

// Fondo y distribución compartidos por las pantallas.
@Composable
private fun Pagina(
    titulo: String,
    descripcion: String = "",
    contenido: @Composable ColumnScope.() -> Unit
) {
    Column(
        Modifier
            .fillMaxSize()
            .background(NavColores.Crema)
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Titulo(titulo, descripcion)
        contenido()
    }
}

// Botón principal reutilizable.
@Composable
private fun Boton(texto: String, accion: () -> Unit) {
    Button(
        onClick = accion,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = NavColores.Burdeo
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Text(texto)
    }
}

// INICIO DE SESIÓN
@Composable
fun LoginScreen(onIngresar: () -> Unit, onIrARegistro: () -> Unit) {
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    Pagina("Bienvenido a CENAMAD", "Conocimiento e innovación en madera.") {
        OutlinedTextField(
            correo, { correo = it },
            label = { Text("Correo electrónico") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            clave, { clave = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Boton("Ingresar", onIngresar)
        TextButton(onClick = onIrARegistro) {
            Text("Crear cuenta")
        }
    }
}

// REGISTRO
@Composable
fun RegistroScreen(onRegistrar: () -> Unit, onVolver: () -> Unit) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var clave by remember { mutableStateOf("") }

    Pagina("Crear cuenta", "Completa tus datos.") {
        OutlinedTextField(
            nombre, { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            correo, { correo = it },
            label = { Text("Correo") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            clave, { clave = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth()
        )
        Boton("Registrarme", onRegistrar)
        TextButton(onClick = onVolver) { Text("Volver") }
    }
}

// PANTALLA PRINCIPAL
@Composable
fun InicioScreen(onExplorar: () -> Unit, onVerIndicadores: () -> Unit) {
    Column(
        Modifier
            .fillMaxSize()
            .background(NavColores.BurdeoOscuro)
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(Modifier.height(20.dp))
        Text("CENAMAD", color = NavColores.Blanco, fontSize = 14.sp)
        Text(
            "Innovación que transforma la madera.",
            color = NavColores.Blanco,
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "Investigación, tecnología y sostenibilidad para el futuro.",
            color = NavColores.Blanco
        )
        Button(
            onClick = onExplorar,
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = NavColores.Blanco,
                contentColor = NavColores.Burdeo
            )
        ) { Text("Explorar contenido") }
        OutlinedButton(
            onClick = onVerIndicadores,
            modifier = Modifier.fillMaxWidth()
        ) { Text("Ver indicadores") }
    }
}

// LÍNEAS DE INVESTIGACIÓN
@Composable
fun InvestigacionScreen(onVerLinea: (Int) -> Unit) {
    Pagina("Investigación", "Conoce nuestras áreas de trabajo.") {
        lineas.forEachIndexed { i, nombre ->
            Tarjeta(
                nombre,
                "Investigación aplicada y desarrollo sostenible.",
                { onVerLinea(i + 1) }
            )
        }
    }
}

// INFORMACIÓN DE CENAMAD
@Composable
fun CenamadScreen() {
    Pagina("Sobre CENAMAD", "Ciencia para una industria más sostenible.") {
        Tarjeta("Nuestra misión", "Impulsar la investigación y la innovación.")
        Tarjeta("Nuestro trabajo", "Desarrollo tecnológico y formación de capital humano.")
        Tarjeta("Colaboración", "Conectar investigadores, proyectos y publicaciones.")
    }
}

// NOVEDADES
@Composable
fun NovedadesScreen() {
    Pagina("Novedades", "Noticias y actividades de ejemplo.") {
        Tarjeta("Seminario de madera", "Actividad sobre construcción sostenible.")
        Tarjeta("Nueva publicación", "Material técnico de investigación.")
        Tarjeta("Encuentro científico", "Colaboración entre investigadores.")
    }
}

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
            publicaciones.forEachIndexed { i, nombre ->
                if (nombre.contains(busqueda, ignoreCase = true)) {
                    Tarjeta(nombre, "Publicación de ejemplo", {
                        onVerPublicacion(i + 1)
                    })
                }
            }
        }

        if (filtro == "Todo" || filtro == "Investigadores") {
            investigadores.forEachIndexed { i, nombre ->
                if (nombre.contains(busqueda, ignoreCase = true)) {
                    Tarjeta(nombre, "Investigador de ejemplo", {
                        onVerInvestigador(i + 1)
                    })
                }
            }
        }

        if (filtro == "Todo" || filtro == "Proyectos") {
            proyectos.forEachIndexed { i, nombre ->
                if (nombre.contains(busqueda, ignoreCase = true)) {
                    Tarjeta(nombre, "Proyecto de ejemplo", {
                        onVerProyecto(i + 1)
                    })
                }
            }
        }
    }
}

// GUARDADOS
@Composable
fun GuardadosScreen(onVerPublicacion: (Int) -> Unit) {
    Pagina("Guardados", "Acceso rápido a contenido.") {
        Tarjeta("Diseño sostenible en madera", "Publicación de ejemplo.", {
            onVerPublicacion(1)
        })
    }
}

// PERFIL
@Composable
fun PerfilScreen(onCerrarSesion: () -> Unit) {
    Pagina("Mi perfil", "Tu espacio personal.") {
        Tarjeta("Usuario de prueba", "estudiante@ejemplo.cl")
        Boton("Cerrar sesión", onCerrarSesion)
    }
}

// DETALLE DE UNA LÍNEA
@Composable
fun LineaDetalleScreen(
    lineaId: Int,
    onVerInvestigador: (Int) -> Unit,
    onVerProyecto: (Int) -> Unit,
    onVerPublicacion: (Int) -> Unit
) {
    val i = (lineaId - 1).coerceIn(0, lineas.lastIndex)

    Pagina(lineas[i], "Detalle de la línea de investigación.") {
        Tarjeta(investigadores[i], "Investigador relacionado.", {
            onVerInvestigador(i + 1)
        })
        Tarjeta(proyectos[i], "Proyecto relacionado.", {
            onVerProyecto(i + 1)
        })
        Tarjeta(publicaciones[i], "Publicación relacionada.", {
            onVerPublicacion(i + 1)
        })
    }
}

// DETALLE DE INVESTIGADOR
@Composable
fun InvestigadorScreen(
    investigadorId: Int,
    onVerProyecto: (Int) -> Unit,
    onVerPublicacion: (Int) -> Unit
) {
    val i = (investigadorId - 1).coerceIn(0, investigadores.lastIndex)

    Pagina(investigadores[i], "Investigador de ejemplo.") {
        Tarjeta(proyectos[i], "Proyecto relacionado.", {
            onVerProyecto(i + 1)
        })
        Tarjeta(publicaciones[i], "Publicación relacionada.", {
            onVerPublicacion(i + 1)
        })
    }
}

// DETALLE DE PROYECTO
@Composable
fun ProyectoScreen(proyectoId: Int, onVerInvestigador: (Int) -> Unit) {
    val i = (proyectoId - 1).coerceIn(0, proyectos.lastIndex)

    Pagina(proyectos[i], "Proyecto de demostración.") {
        Tarjeta("Estado", "En desarrollo")
        Tarjeta(investigadores[i], "Investigador relacionado.", {
            onVerInvestigador(i + 1)
        })
    }
}

// DETALLE DE PUBLICACIÓN
@Composable
fun PublicacionScreen(
    publicacionId: Int,
    onVerInvestigador: (Int) -> Unit
) {
    val i = (publicacionId - 1).coerceIn(0, publicaciones.lastIndex)

    Pagina(publicaciones[i], "Publicación de demostración.") {
        Tarjeta("Tipo", "Contenido científico")
        Tarjeta(investigadores[i], "Autor relacionado.", {
            onVerInvestigador(i + 1)
        })
    }
}

// INDICADORES
@Composable
fun IndicadoresScreen() {
    Pagina("Indicadores", "Cifras ficticias para mostrar la interfaz.") {
        Tarjeta("03 · Líneas", "Áreas de investigación.")
        Tarjeta("03 · Investigadores", "Personas de ejemplo.")
        Tarjeta("03 · Proyectos", "Proyectos de ejemplo.")
        Tarjeta("03 · Publicaciones", "Publicaciones de ejemplo.")
    }
}
