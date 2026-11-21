package com.example.eva2.model

import com.google.gson.annotations.SerializedName

data class UbicacionDto(
    val id: Int? = null,
    @SerializedName("created_at")
    val createdAt: Long? = null,
    val nombre: String,
    val direccion: String,
    val comuna: String,
    val ciudad: String,
    val latitud: Double,
    val longitud: Double,
    val referencia: String? = null,
    val descripcion: String? = null
)