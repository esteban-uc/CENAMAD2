package com.example.eva2.repository

object ValidadorFormulario {

    private val patronCorreo = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")
    private val patronNombre = Regex("^[A-Za-zÁÉÍÓÚáéíóúÑñÜü ]+$")

    fun validarNombre(nombre: String): String? {
        val nombreLimpio = nombre.trim()
        return when {
            nombreLimpio.isEmpty() -> "Ingresa tu nombre"
            nombreLimpio.length < 3 -> "El nombre debe tener al menos 3 letras"
            nombreLimpio.length > 50 -> "El nombre no puede superar los 50 caracteres"
            !patronNombre.matches(nombreLimpio) -> "El nombre solo puede tener letras y espacios"
            else -> null
        }
    }

    private val dominiosPermitidos = listOf("gmail.com")

    fun validarCorreo(correo: String): String? {
        val correoLimpio = correo.trim().lowercase()
        return when {
            correoLimpio.isEmpty() -> "Ingresa tu correo"
            !patronCorreo.matches(correoLimpio) -> "El correo no tiene un formato válido"
            correoLimpio.substringAfter("@") !in dominiosPermitidos -> "Usa un correo como nombre@gmail.com"
            else -> null
        }
    }

    fun validarContrasenaLogin(contrasena: String): String? =
        if (contrasena.isBlank()) "Ingresa tu contraseña" else null

    fun validarContrasenaRegistro(contrasena: String): String? = when {
        contrasena.isBlank() -> "Ingresa una contraseña"
        contrasena.length < 8 -> "Debe tener al menos 8 caracteres"
        contrasena.none { it.isUpperCase() } -> "Debe tener al menos una mayúscula"
        contrasena.none { it.isDigit() } -> "Debe tener al menos un número"
        else -> null
    }

    fun validarConfirmacion(contrasena: String, confirmacion: String): String? = when {
        confirmacion.isBlank() -> "Repite tu contraseña"
        confirmacion != contrasena -> "Las contraseñas no coinciden"
        else -> null
    }
}