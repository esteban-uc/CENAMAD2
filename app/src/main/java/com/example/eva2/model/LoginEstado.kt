package com.example.eva2.model


data class LoginEstado(
    val correo: String = "",
    val contrasena: String = "",
    val errorCorreo: String? = null,
    val errorContrasena: String? = null,
    val errorGeneral: String? = null,  // "Correo o contraseña incorrectos"
    val cargando: Boolean = false,      // es true mientras se consulta la base de datos
    val intentosFallidos: Int = 0,      // aumenta en cada intento fallido y activa la sacudida
    val ingresoExitoso: Boolean = false // es true cuando el correo y la contraseña coinciden
)
