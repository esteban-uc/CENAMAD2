package com.example.DuocUc.model

data class Ubicacion(
    val nombre: String,
    val direccion: String,
    val comuna: String,
    val ciudad: String,
    val latitud: Double,
    val longitud: Double,
    val referencia: String = "",   // ej: "Campus San Joaquín UC"
    val descripcion: String = ""   // texto corto que aparece al tocar el marcador
) {
    // Dirección lista para mostrar en pantalla o buscar en Google Maps
    val direccionCompleta: String
        get() = "$direccion, $comuna, $ciudad"
}
