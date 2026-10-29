package com.example.eva2.ui.nav

object Rutas {
    const val LOGIN = "login"
    const val REGISTRO = "registro"

    const val INICIO = "inicio"               // se muestra como "Portada"
    const val INVESTIGACION = "investigacion"
    const val CENAMAD = "cenamad"
    const val NOVEDADES = "novedades"
    const val EXPLORAR = "explorar"
    const val GUARDADOS = "guardados"
    const val PERFIL = "perfil"
    const val ARG_ID = "id"
    const val LINEA_DETALLE = "linea/{$ARG_ID}"
    const val INVESTIGADOR_DETALLE = "investigador/{$ARG_ID}"
    const val PROYECTO_DETALLE = "proyecto/{$ARG_ID}"
    const val PUBLICACION_DETALLE = "publicacion/{$ARG_ID}"


    const val INDICADORES = "indicadores"


    fun linea(id: Int) = "linea/$id"
    fun investigador(id: Int) = "investigador/$id"
    fun proyecto(id: Int) = "proyecto/$id"
    fun publicacion(id: Int) = "publicacion/$id"
}
