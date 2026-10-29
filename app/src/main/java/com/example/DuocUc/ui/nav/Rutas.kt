package com.example.DuocUc.ui.nav

/*
 * ============================================================================
 * NAVEGACIÓN · Rutas.kt
 * Ubicación: ui/nav/Rutas.kt
 * ----------------------------------------------------------------------------
 * Todos los nombres de pantalla de la app en un solo lugar.
 *
 *  ┌ SIN BARRAS ........ LOGIN, REGISTRO
 *  ├ SECCIONES (pestañas de arriba del mockup) .. INICIO ("Portada"),
 *  │                     INVESTIGACION, CENAMAD, NOVEDADES
 *  ├ BARRA INFERIOR .... INICIO, EXPLORAR, GUARDADOS, PERFIL
 *  └ DETALLES .......... LINEA, INVESTIGADOR, PROYECTO, INDICADORES y
 *                        PUBLICACION (se muestra como "Vista previa", a pantalla completa)
 *
 * Para agregar una pantalla: 1) su nombre aquí, 2) su bloque composable en
 * ui/Navegacion.kt (función GrafoNavegacion).
 * ============================================================================
 */
object Rutas {
    // --- Acceso (sin barras) ---
    const val LOGIN = "login"
    const val REGISTRO = "registro"

    // --- Secciones principales (fila de pestañas superior) ---
    const val INICIO = "inicio"               // se muestra como "Portada"
    const val INVESTIGACION = "investigacion"
    const val CENAMAD = "cenamad"
    const val NOVEDADES = "novedades"

    // --- Otras pantallas de la barra inferior ---
    const val EXPLORAR = "explorar"
    const val GUARDADOS = "guardados"
    const val PERFIL = "perfil"

    // --- Detalles (reciben un id) ---
    const val ARG_ID = "id"
    const val LINEA_DETALLE = "linea/{$ARG_ID}"
    const val INVESTIGADOR_DETALLE = "investigador/{$ARG_ID}"
    const val PROYECTO_DETALLE = "proyecto/{$ARG_ID}"
    const val PUBLICACION_DETALLE = "publicacion/{$ARG_ID}"

    // --- Otras ---
    const val INDICADORES = "indicadores"

    // --- Armar la ruta con el id real. Ej: navController.navigate(Rutas.linea(3)) ---
    fun linea(id: Int) = "linea/$id"
    fun investigador(id: Int) = "investigador/$id"
    fun proyecto(id: Int) = "proyecto/$id"
    fun publicacion(id: Int) = "publicacion/$id"
}
