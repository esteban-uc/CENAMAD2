package com.example.eva2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eva2.model.LoginEstado
import com.example.eva2.repository.UsuarioRepository
import com.example.eva2.repository.ValidadorFormulario
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(private val usuarioRepository: UsuarioRepository) : ViewModel() {

    private val _estado = MutableStateFlow(LoginEstado())
    val estado: StateFlow<LoginEstado> = _estado.asStateFlow()
    fun alCambiarCorreo(nuevoCorreo: String) {
        _estado.update { it.copy(correo = nuevoCorreo, errorCorreo = null, errorGeneral = null) }
    }
    fun alCambiarContrasena(nuevaContrasena: String) {
        _estado.update { it.copy(contrasena = nuevaContrasena, errorContrasena = null, errorGeneral = null) }
    }

    fun iniciarSesion() {
        val estadoActual = _estado.value

        if (estadoActual.cargando) return

        val errorCorreo = ValidadorFormulario.validarCorreo(estadoActual.correo)
        val errorContrasena = ValidadorFormulario.validarContrasenaLogin(estadoActual.contrasena)

        if (errorCorreo != null || errorContrasena != null) {
            _estado.update {
                it.copy(
                    errorCorreo = errorCorreo,
                    errorContrasena = errorContrasena,
                    intentosFallidos = it.intentosFallidos + 1
                )
            }
            return
        }

        _estado.update { it.copy(cargando = true, errorGeneral = null) }

        viewModelScope.launch {
            val usuario = usuarioRepository.iniciarSesion(estadoActual.correo, estadoActual.contrasena)
            _estado.update {
                if (usuario != null) {
                    it.copy(cargando = false, ingresoExitoso = true)
                } else {
                    it.copy(
                        cargando = false,
                        errorGeneral = "Correo o contraseña incorrectos",
                        intentosFallidos = it.intentosFallidos + 1
                    )
                }
            }
        }
    }
    companion object {
        fun fabrica(usuarioRepository: UsuarioRepository) = viewModelFactory {
            initializer { LoginViewModel(usuarioRepository) }
        }
    }
}
