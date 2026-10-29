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
    val cargando: Boolean = false,        // es true mientras se guarda en la base de datos
    val registroExitoso: Boolean = false  // es true cuando la cuenta quedó guardada
)
