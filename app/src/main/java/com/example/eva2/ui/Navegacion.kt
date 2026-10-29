package com.example.eva2.ui

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.eva2.repository.UsuarioRepository
import com.example.eva2.ui.nav.BarraInferior
import com.example.eva2.ui.nav.BarraSuperiorDetalle
import com.example.eva2.ui.nav.BarraSuperiorPrincipal
import com.example.eva2.ui.nav.BarraSuperiorVistaPrevia
import com.example.eva2.ui.nav.ContenidoMenuLateral
import com.example.eva2.ui.nav.Rutas
import com.example.eva2.ui.nav.RutasPrincipales
import com.example.eva2.ui.nav.Secciones
import com.example.eva2.ui.temporal.*
import com.example.eva2.ui.usuario.CargaSesionScreen
import com.example.eva2.ui.usuario.DialogoCerrarSesion
import com.example.eva2.ui.usuario.LoginScreen
import com.example.eva2.ui.usuario.PerfilUsuarioScreen
import com.example.eva2.ui.usuario.RegistroScreen
import com.example.eva2.viewmodel.LoginViewModel
import com.example.eva2.viewmodel.PerfilViewModel
import com.example.eva2.viewmodel.RegistroViewModel
import kotlinx.coroutines.launch

/*
 * ============================================================================
 * NAVEGACIÓN · Navegacion.kt   (ARCHIVO PRINCIPAL)
 * Ubicación: ui/Navegacion.kt
 * ----------------------------------------------------------------------------
 * MainActivity solo llama a:  Navegacion(usuarioRepository)
 *
 * MAPA DE ARCHIVOS DE LA NAVEGACIÓN
 *   ui/Navegacion.kt ............. (este) arma todo + mapa de pantallas
 *   ui/nav/Rutas.kt .............. nombres de las pantallas
 *   ui/nav/NavColores.kt ......... colores (burdeo y crema)
 *   ui/nav/Iconos.kt ............. íconos dibujados a mano (sin librerías)
 *   ui/nav/BarraSuperior.kt ...... logo + hamburguesa / flecha volver / vista previa
 *   ui/nav/FilaSecciones.kt ...... pestañas Portada·Investigación·CENAMAD·Novedades
 *   ui/nav/BarraInferior.kt ...... Inicio · Explorar · Guardados · Perfil
 *   ui/nav/MenuLateral.kt ........ menú que abre la hamburguesa
 *   ui/usuario/ .................. login, registro, perfil, carga de sesión y modal de cierre (con Room)
 *   ui/temporal/ ................. pantallas de prueba (se reemplazan)
 *
 * ESTE ARCHIVO TIENE, EN ORDEN:
 *   A) Navegacion() ........ esqueleto: menú lateral + barras + contenido
 *   B) GrafoNavegacion() ... qué pantalla va con qué ruta y qué hace cada botón
 *   C) Funciones de ayuda .. cambiar de sección, cerrar sesión, títulos
 * ============================================================================
 */

// ============================================================================
// A) ESQUELETO DE LA APP
// ============================================================================
// usuarioRepository: acceso a los usuarios en Room y a la sesión actual (se crea en MainActivity)
@Composable
fun Navegacion(usuarioRepository: UsuarioRepository) {
    // Al abrir la app se revisa si quedó una sesión guardada en Room:
    // si hay, se entra directo a INICIO; si no, se parte en LOGIN.
    var revisandoSesion by remember { mutableStateOf(true) }
    var pantallaInicial by remember { mutableStateOf(Rutas.LOGIN) }
    LaunchedEffect(Unit) {
        if (usuarioRepository.restaurarSesion() != null) pantallaInicial = Rutas.INICIO
        revisandoSesion = false
    }
    if (revisandoSesion) {
        CargaSesionScreen()
        return
    }

    // El "control remoto" de la navegación: se crea una vez y se comparte.
    val navController = rememberNavController()

    // Estado del menú lateral (abierto/cerrado) y un scope para abrirlo/cerrarlo.
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val contexto = LocalContext.current   // se usa para abrir el menú "Compartir" del celular

    // Modal de confirmación al cerrar sesión (desde el menú lateral o desde Perfil).
    var mostrarDialogoCierre by remember { mutableStateOf(false) }

    // Pantalla actual (se actualiza sola cada vez que se navega).
    val entradaActual by navController.currentBackStackEntryAsState()
    val ruta = entradaActual?.destination?.route

    // ¿Qué barras mostrar según la pantalla?
    val esAutenticacion = ruta == Rutas.LOGIN || ruta == Rutas.REGISTRO
    val esSeccion = Secciones.any { it.ruta == ruta }   // Portada, Investigación, CENAMAD, Novedades
    val esPrincipal = ruta in RutasPrincipales          // las 4 de abajo + secciones

    // ---- MENÚ LATERAL (ver ui/nav/MenuLateral.kt) ----
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = esPrincipal,   // solo se abre deslizando en pantallas principales
        drawerContent = {
            ContenidoMenuLateral(
                rutaActual = ruta,
                onNavegar = { destino ->
                    scope.launch { drawerState.close() }
                    if (destino == Rutas.INDICADORES) {
                        navController.navigate(destino)
                    } else {
                        navController.irASeccion(destino)
                    }
                },
                onCerrarSesion = {
                    scope.launch { drawerState.close() }
                    mostrarDialogoCierre = true
                }
            )
        }
    ) {
        Scaffold(
            // ---- BARRA SUPERIOR (ver ui/nav/BarraSuperior.kt) ----
            topBar = {
                when {
                    esAutenticacion -> Unit   // login y registro: sin barra
                    esPrincipal -> BarraSuperiorPrincipal(
                        rutaActual = ruta,
                        mostrarSecciones = esSeccion,
                        onMenu = { scope.launch { drawerState.open() } },
                        onSeccion = { navController.irASeccion(it) }
                    )
                    // "Vista previa" de una publicación: barra con X y compartir
                    ruta == Rutas.PUBLICACION_DETALLE -> BarraSuperiorVistaPrevia(
                        titulo = "Vista previa",
                        onCerrar = { navController.popBackStack() },
                        onCompartir = {
                            val id = entradaActual?.arguments?.getInt(Rutas.ARG_ID) ?: 0
                            compartirPublicacion(contexto, id)
                        }
                    )
                    else -> BarraSuperiorDetalle(
                        titulo = tituloDeRuta(ruta),
                        onAtras = { navController.popBackStack() }
                    )
                }
            },
            // ---- BARRA INFERIOR (ver ui/nav/BarraInferior.kt) ----
            bottomBar = {
                AnimatedVisibility(
                    visible = esPrincipal,
                    enter = slideInVertically(initialOffsetY = { it }) + fadeIn(),
                    exit = slideOutVertically(targetOffsetY = { it }) + fadeOut()
                ) {
                    BarraInferior(
                        rutaActual = ruta,
                        onItemClick = { navController.irASeccion(it) }
                    )
                }
            }
        ) { padding ->
            // ---- CONTENIDO ----
            GrafoNavegacion(
                navController = navController,
                usuarioRepository = usuarioRepository,
                pantallaInicial = pantallaInicial,
                onCerrarSesion = { mostrarDialogoCierre = true },
                modifier = Modifier.padding(padding)
            )
        }
    }

    // ---- MODAL "¿CERRAR SESIÓN?" (ver ui/usuario/DialogoCerrarSesion.kt) ----
    if (mostrarDialogoCierre) {
        DialogoCerrarSesion(
            onConfirmar = {
                mostrarDialogoCierre = false
                scope.launch {
                    usuarioRepository.cerrarSesion()   // borra la sesión guardada en Room
                    navController.irAlLogin()          // vuelve al login
                }
            },
            onCancelar = { mostrarDialogoCierre = false }
        )
    }
}

// ============================================================================
// B) MAPA DE PANTALLAS
// ============================================================================

private const val DURACION_MS = 300

// Argumento "id" que llevan las pantallas de detalle (tipo Int)
private val argumentoId = listOf(navArgument(Rutas.ARG_ID) { type = NavType.IntType })

// Lee el id de una pantalla de detalle
private fun NavBackStackEntry.id(): Int = arguments?.getInt(Rutas.ARG_ID) ?: 0

@Composable
private fun GrafoNavegacion(
    navController: NavHostController,
    usuarioRepository: UsuarioRepository,
    pantallaInicial: String,
    onCerrarSesion: () -> Unit,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = pantallaInicial,   // <- LOGIN, o INICIO si había sesión guardada
        modifier = modifier,

        // ---- ANIMACIONES ENTRE PANTALLAS ----
        // Entre pestañas/secciones: solo desvanecer.
        // Hacia un detalle: deslizar + desvanecer.
        enterTransition = {
            if (esCambioEntrePrincipales()) fadeIn(tween(250))
            else slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(DURACION_MS)) +
                    fadeIn(tween(DURACION_MS))
        },
        exitTransition = {
            if (esCambioEntrePrincipales() || haciaVistaPrevia()) fadeOut(tween(250))
            else slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Start, tween(DURACION_MS)) +
                    fadeOut(tween(DURACION_MS))
        },
        popEnterTransition = {
            if (esCambioEntrePrincipales() || desdeVistaPrevia()) fadeIn(tween(250))
            else slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(DURACION_MS)) +
                    fadeIn(tween(DURACION_MS))
        },
        popExitTransition = {
            if (esCambioEntrePrincipales()) fadeOut(tween(250))
            else slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.End, tween(DURACION_MS)) +
                    fadeOut(tween(DURACION_MS))
        }
    ) {

        // ---------------------------------------------------------------
        // 1) ACCESO
        // ---------------------------------------------------------------

        // LOGIN: al ingresar entra a INICIO y borra el login del historial
        // (así el botón "atrás" no vuelve al login).
        composable(Rutas.LOGIN) {
            val loginViewModel: LoginViewModel =
                viewModel(factory = LoginViewModel.fabrica(usuarioRepository))
            LoginScreen(
                viewModel = loginViewModel,
                onIngresar = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onIrARegistro = { navController.navigate(Rutas.REGISTRO) },
                // VISITANTE: entra sin sesión; en Perfil verá "Hola, visitante"
                onContinuarComoVisitante = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        // REGISTRO: al registrarse entra directo a INICIO.
        composable(Rutas.REGISTRO) {
            val registroViewModel: RegistroViewModel =
                viewModel(factory = RegistroViewModel.fabrica(usuarioRepository))
            RegistroScreen(
                viewModel = registroViewModel,
                onRegistrado = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                },
                onVolver = { navController.popBackStack() }
            )
        }

        // ---------------------------------------------------------------
        // 2) SECCIONES (pestañas de arriba)
        // ---------------------------------------------------------------

        // INICIO ("Portada"): botón "Explorar biblioteca" del mockup -> pestaña Explorar.
        composable(Rutas.INICIO) {
            InicioScreen(
                onExplorar = { navController.irASeccion(Rutas.EXPLORAR) },
                onVerIndicadores = { navController.navigate(Rutas.INDICADORES) }
            )
        }

        composable(Rutas.INVESTIGACION) {
            InvestigacionScreen(
                onVerLinea = { id -> navController.navigate(Rutas.linea(id)) }
            )
        }

        composable(Rutas.CENAMAD) { UbicacionScreen() }
        composable(Rutas.NOVEDADES) { NovedadesScreen() }

        composable(Rutas.NOVEDADES) { NovedadesScreen() }

        // ---------------------------------------------------------------
        // 3) OTRAS PANTALLAS DE LA BARRA INFERIOR
        // ---------------------------------------------------------------

        // EXPLORAR: buscador + filtros + lista de publicaciones (mockup).
        // Tocar una publicación abre la "Vista previa".
        composable(Rutas.EXPLORAR) {
            ExplorarScreen(
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) },
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) },
                onVerProyecto = { id -> navController.navigate(Rutas.proyecto(id)) }
            )
        }

        // GUARDADOS: publicaciones marcadas con el marcador.
        composable(Rutas.GUARDADOS) {
            GuardadosScreen(
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) }
            )
        }

        // PERFIL: saluda al usuario (o al visitante) y lleva a sus secciones
        composable(Rutas.PERFIL) {
            val perfilViewModel: PerfilViewModel =
                viewModel(factory = PerfilViewModel.fabrica(usuarioRepository))
            PerfilUsuarioScreen(
                viewModel = perfilViewModel,
                onIniciarSesion = { navController.irAlLogin() },
                onVerGuardados = { navController.irASeccion(Rutas.GUARDADOS) },
                onVerIndicadores = { navController.navigate(Rutas.INDICADORES) },
                onAcercaDe = { navController.irASeccion(Rutas.CENAMAD) },
                onCerrarSesion = onCerrarSesion   // abre el modal de confirmación
            )
        }

        // ---------------------------------------------------------------
        // 4) DETALLES (reciben un id; se lee con entrada.id())
        // ---------------------------------------------------------------

        // Línea de investigación -> sus investigadores, proyectos y publicaciones.
        composable(Rutas.LINEA_DETALLE, arguments = argumentoId) { entrada ->
            LineaDetalleScreen(
                lineaId = entrada.id(),
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) },
                onVerProyecto = { id -> navController.navigate(Rutas.proyecto(id)) },
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) }
            )
        }

        // Investigador -> sus proyectos y publicaciones.
        composable(Rutas.INVESTIGADOR_DETALLE, arguments = argumentoId) { entrada ->
            InvestigadorScreen(
                investigadorId = entrada.id(),
                onVerProyecto = { id -> navController.navigate(Rutas.proyecto(id)) },
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) }
            )
        }

        // Proyecto -> investigador responsable.
        composable(Rutas.PROYECTO_DETALLE, arguments = argumentoId) { entrada ->
            ProyectoScreen(
                proyectoId = entrada.id(),
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) }
            )
        }

        // Publicación = "Vista previa" a pantalla completa.
        // Sube desde abajo (como un modal) y se cierra con la "X" o bajándola.
        composable(
            route = Rutas.PUBLICACION_DETALLE,
            arguments = argumentoId,
            enterTransition = {
                slideInVertically(tween(DURACION_MS)) { it } + fadeIn(tween(DURACION_MS))
            },
            popExitTransition = {
                slideOutVertically(tween(DURACION_MS)) { it } + fadeOut(tween(DURACION_MS))
            }
        ) { entrada ->
            PublicacionScreen(
                publicacionId = entrada.id(),
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) }
            )
        }

        // Indicadores (se abre desde el menú lateral o desde Inicio).
        composable(Rutas.INDICADORES) { IndicadoresScreen() }
    }
}

// ============================================================================
// C) FUNCIONES DE AYUDA
// ============================================================================

/**
 * Cambia de sección/pestaña sin apilar pantallas repetidas:
 *  - popUpTo(INICIO) + saveState/restoreState: cada sección recuerda su estado
 *  - launchSingleTop: tocar la sección actual no la abre dos veces
 */
private fun NavHostController.irASeccion(ruta: String) {
    navigate(ruta) {
        popUpTo(Rutas.INICIO) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

/** Borra todo el historial y abre el login (al cerrar sesión o al entrar desde visitante). */
private fun NavHostController.irAlLogin() {
    navigate(Rutas.LOGIN) {
        popUpTo(Rutas.INICIO) { inclusive = true }
    }
}

/** true si la transición es entre dos pantallas principales (para usar solo fade). */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.esCambioEntrePrincipales(): Boolean {
    val desde = initialState.destination.route
    val hacia = targetState.destination.route
    return desde in RutasPrincipales && hacia in RutasPrincipales
}

/** true si se está abriendo la "Vista previa" (que entra desde abajo). */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.haciaVistaPrevia(): Boolean =
    targetState.destination.route == Rutas.PUBLICACION_DETALLE

/** true si se está cerrando la "Vista previa". */
private fun AnimatedContentTransitionScope<NavBackStackEntry>.desdeVistaPrevia(): Boolean =
    initialState.destination.route == Rutas.PUBLICACION_DETALLE

/** Botón compartir de la vista previa: abre el menú "Compartir" del celular. */
private fun compartirPublicacion(contexto: Context, id: Int) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Mira esta publicación de CENAMAD (n° $id)")
    }
    contexto.startActivity(Intent.createChooser(intent, "Compartir publicación"))
}

/** Título que se muestra en la barra de las pantallas de detalle. */
private fun tituloDeRuta(ruta: String?): String = when (ruta) {
    Rutas.LINEA_DETALLE -> "Línea de investigación"
    Rutas.INVESTIGADOR_DETALLE -> "Investigador"
    Rutas.PROYECTO_DETALLE -> "Proyecto"
    Rutas.PUBLICACION_DETALLE -> "Publicación"
    Rutas.INDICADORES -> "Indicadores"
    else -> "CENAMAD"
}
