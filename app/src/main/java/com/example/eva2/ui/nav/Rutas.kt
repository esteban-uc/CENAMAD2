package com.example.eva2.ui.nav


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
