package com.example.eva2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eva2.model.RegistroEstado
import com.example.eva2.repository.UsuarioRepository
import com.example.eva2.repository.ValidadorFormulario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegistroViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {

    private val _estado = MutableStateFlow(RegistroEstado())
    val estado: StateFlow<RegistroEstado> = _estado.asStateFlow()

    fun alCambiarNombre(nuevoNombre: String) {
        _estado.update { it.copy(nombre = nuevoNombre, errorNombre = null) }
    }

    fun alCambiarCorreo(nuevoCorreo: String) {
        _estado.update { it.copy(correo = nuevoCorreo, errorCorreo = null) }
    }

    fun alCambiarContrasena(nuevaContrasena: String) {
        _estado.update { it.copy(contrasena = nuevaContrasena, errorContrasena = null) }
    }

    fun alCambiarConfirmacion(nuevaConfirmacion: String) {
        _estado.update { it.copy(confirmacion = nuevaConfirmacion, errorConfirmacion = null) }
    }

    fun registrar() {
        val estadoActual = _estado.value
        if (estadoActual.cargando) return

        val errorNombre = ValidadorFormulario.validarNombre(estadoActual.nombre)
        val errorCorreo = ValidadorFormulario.validarCorreo(estadoActual.correo)
        val errorContrasena = ValidadorFormulario.validarContrasenaRegistro(estadoActual.contrasena)
        val errorConfirmacion =
            ValidadorFormulario.validarConfirmacion(estadoActual.contrasena, estadoActual.confirmacion)

        if (listOf(errorNombre, errorCorreo, errorContrasena, errorConfirmacion).any { it != null }) {
            _estado.update {
                it.copy(
                    errorNombre = errorNombre,
                    errorCorreo = errorCorreo,
                    errorContrasena = errorContrasena,
                    errorConfirmacion = errorConfirmacion
                )
            }
            return
        }

        _estado.update { it.copy(cargando = true) }

        viewModelScope.launch {
            val usuario = usuarioRepository.registrar(
                estadoActual.nombre,
                estadoActual.correo,
                estadoActual.contrasena
            )
            _estado.update {
                if (usuario != null) {
                    it.copy(cargando = false, registroExitoso = true)
                } else {
                    it.copy(cargando = false, errorCorreo = "Este correo ya está registrado")
                }
            }
        }
    }

    companion object {
        fun fabrica(usuarioRepository: UsuarioRepository) = viewModelFactory {
            initializer { RegistroViewModel(usuarioRepository) }
        }
    }
}
