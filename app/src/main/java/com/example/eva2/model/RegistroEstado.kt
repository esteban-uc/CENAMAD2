package com.example.eva2.model


data class RegistroEstado(
    val nombre: String = "",
    val correo: String = "",
    val contrasena: String = "",
    val confirmacion: String = "",
    val errorNombre: String? = null,
    val errorCorreo: String? = null,
    val errorContrasena: String? = null,
    val errorConfirmacion: String? = null,
    val cargando: Boolean = false,
    val registroExitoso: Boolean = false
)
