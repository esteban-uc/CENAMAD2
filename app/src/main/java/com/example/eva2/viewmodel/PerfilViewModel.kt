package com.example.eva2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.eva2.model.Usuario
import com.example.eva2.repository.UsuarioRepository
import kotlinx.coroutines.flow.StateFlow

class PerfilViewModel(usuarioRepository: UsuarioRepository) : ViewModel() {

    val usuario: StateFlow<Usuario?> = usuarioRepository.usuarioActual

    companion object {
        fun fabrica(usuarioRepository: UsuarioRepository) = viewModelFactory {
            initializer { PerfilViewModel(usuarioRepository) }
        }
    }
}
