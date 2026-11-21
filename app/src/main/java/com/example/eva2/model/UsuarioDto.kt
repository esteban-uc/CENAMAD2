package com.example.eva2.model

import com.google.gson.annotations.SerializedName

data class UsuarioDto(
    val id: Int? = null,
    @SerializedName("created_at")
    val createdAt: Long? = null,
    val nombre: String,
    val correo: String,
    val contrasenaHash: String? = null
)