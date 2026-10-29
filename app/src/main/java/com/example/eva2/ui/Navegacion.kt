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
@Composable
fun Navegacion(usuarioRepository: UsuarioRepository) {

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


    val navController = rememberNavController()


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val contexto = LocalContext.current

    var mostrarDialogoCierre by remember { mutableStateOf(false) }

    val entradaActual by navController.currentBackStackEntryAsState()
    val ruta = entradaActual?.destination?.route


    val esAutenticacion = ruta == Rutas.LOGIN || ruta == Rutas.REGISTRO
    val esSeccion = Secciones.any { it.ruta == ruta }
    val esPrincipal = ruta in RutasPrincipales

    // Menu Lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = esPrincipal,
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
            // BARRA SUPERIOR
            topBar = {
                when {
                    esAutenticacion -> Unit
                    esPrincipal -> BarraSuperiorPrincipal(
                        rutaActual = ruta,
                        mostrarSecciones = esSeccion,
                        onMenu = { scope.launch { drawerState.open() } },
                        onSeccion = { navController.irASeccion(it) }
                    )
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
            // BARRA INFERIOR
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
            // CONTENIDO
            GrafoNavegacion(
                navController = navController,
                usuarioRepository = usuarioRepository,
                pantallaInicial = pantallaInicial,
                onCerrarSesion = { mostrarDialogoCierre = true },
                modifier = Modifier.padding(padding)
            )
        }
    }

    if (mostrarDialogoCierre) {
        DialogoCerrarSesion(
            onConfirmar = {
                mostrarDialogoCierre = false
                scope.launch {
                    usuarioRepository.cerrarSesion()
                    navController.irAlLogin()
                }
            },
            onCancelar = { mostrarDialogoCierre = false }
        )
    }
}


// MAPA DE PANTALLAS


private const val DURACION_MS = 300


private val argumentoId = listOf(navArgument(Rutas.ARG_ID) { type = NavType.IntType })
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
        startDestination = pantallaInicial,
        modifier = modifier,

        // Animaciones
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


        // ACCESO
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
                onContinuarComoVisitante = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

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


        // SECCIONE


        // INICIO
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
        composable(Rutas.EXPLORAR) {
            ExplorarScreen(
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) },
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) },
                onVerProyecto = { id -> navController.navigate(Rutas.proyecto(id)) }
            )
        }

        // GUARDADOS
        composable(Rutas.GUARDADOS) {
            GuardadosScreen(
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) }
            )
        }

        // PERFIL
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


        // DETALLES

        composable(Rutas.LINEA_DETALLE, arguments = argumentoId) { entrada ->
            LineaDetalleScreen(
                lineaId = entrada.id(),
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) },
                onVerProyecto = { id -> navController.navigate(Rutas.proyecto(id)) },
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) }
            )
        }

        composable(Rutas.INVESTIGADOR_DETALLE, arguments = argumentoId) { entrada ->
            InvestigadorScreen(
                investigadorId = entrada.id(),
                onVerProyecto = { id -> navController.navigate(Rutas.proyecto(id)) },
                onVerPublicacion = { id -> navController.navigate(Rutas.publicacion(id)) }
            )
        }
        composable(Rutas.PROYECTO_DETALLE, arguments = argumentoId) { entrada ->
            ProyectoScreen(
                proyectoId = entrada.id(),
                onVerInvestigador = { id -> navController.navigate(Rutas.investigador(id)) }
            )
        }
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
        composable(Rutas.INDICADORES) { IndicadoresScreen() }
    }
}


// FUNCIONES DE AYUDA

private fun NavHostController.irASeccion(ruta: String) {
    navigate(ruta) {
        popUpTo(Rutas.INICIO) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
private fun NavHostController.irAlLogin() {
    navigate(Rutas.LOGIN) {
        popUpTo(Rutas.INICIO) { inclusive = true }
    }
}
private fun AnimatedContentTransitionScope<NavBackStackEntry>.esCambioEntrePrincipales(): Boolean {
    val desde = initialState.destination.route
    val hacia = targetState.destination.route
    return desde in RutasPrincipales && hacia in RutasPrincipales
}
private fun AnimatedContentTransitionScope<NavBackStackEntry>.haciaVistaPrevia(): Boolean =
    targetState.destination.route == Rutas.PUBLICACION_DETALLE
private fun AnimatedContentTransitionScope<NavBackStackEntry>.desdeVistaPrevia(): Boolean =
    initialState.destination.route == Rutas.PUBLICACION_DETALLE
private fun compartirPublicacion(contexto: Context, id: Int) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, "Mira esta publicación de CENAMAD (n° $id)")
    }
    contexto.startActivity(Intent.createChooser(intent, "Compartir publicación"))
}
private fun tituloDeRuta(ruta: String?): String = when (ruta) {
    Rutas.LINEA_DETALLE -> "Línea de investigación"
    Rutas.INVESTIGADOR_DETALLE -> "Investigador"
    Rutas.PROYECTO_DETALLE -> "Proyecto"
    Rutas.PUBLICACION_DETALLE -> "Publicación"
    Rutas.INDICADORES -> "Indicadores"
    else -> "CENAMAD"
}
