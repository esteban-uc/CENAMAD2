package com.example.eva2.model

data class Ubicacion(
    val nombre: String,
    val direccion: String,
    val comuna: String,
    val ciudad: String,
    val latitud: Double,
    val longitud: Double,
    val referencia: String = "",
    val descripcion: String = ""
) {

    val direccionCompleta: String
        get() = "$direccion, $comuna, $ciudad"
}