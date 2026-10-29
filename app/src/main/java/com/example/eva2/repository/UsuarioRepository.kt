package com.example.eva2.repository

import com.example.eva2.model.Sesion
import com.example.eva2.model.SesionDao
import com.example.eva2.model.Usuario
import com.example.eva2.model.UsuarioDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.security.MessageDigest

class UsuarioRepository(
    private val usuarioDao: UsuarioDao,
    private val sesionDao: SesionDao
) {

    private val _usuarioActual = MutableStateFlow<Usuario?>(null)
    val usuarioActual: StateFlow<Usuario?> = _usuarioActual.asStateFlow()


    suspend fun iniciarSesion(correo: String, contrasena: String): Usuario? {
        val usuario = usuarioDao.buscarPorCorreo(normalizarCorreo(correo)) ?: return null
        if (usuario.contrasenaHash != hashear(contrasena)) return null
        guardarSesion(usuario)
        return usuario
    }

    suspend fun registrar(nombre: String, correo: String, contrasena: String): Usuario? {
        val correoNormalizado = normalizarCorreo(correo)
        if (usuarioDao.buscarPorCorreo(correoNormalizado) != null) return null

        val usuarioNuevo = Usuario(
            nombre = nombre.trim(),
            correo = correoNormalizado,
            contrasenaHash = hashear(contrasena)
        )
        val idAsignado = usuarioDao.insertar(usuarioNuevo)
        val usuarioGuardado = usuarioNuevo.copy(id = idAsignado.toInt())
        guardarSesion(usuarioGuardado)
        return usuarioGuardado
    }

    suspend fun restaurarSesion(): Usuario? {
        val sesion = sesionDao.obtener() ?: return null
        val usuario = usuarioDao.buscarPorId(sesion.usuarioId)
        _usuarioActual.value = usuario
        return usuario
    }

    suspend fun cerrarSesion() {
        sesionDao.borrar()
        _usuarioActual.value = null
    }

    private suspend fun guardarSesion(usuario: Usuario) {
        sesionDao.guardar(Sesion(usuarioId = usuario.id))
        _usuarioActual.value = usuario
    }

    private fun normalizarCorreo(correo: String): String = correo.trim().lowercase()

    private fun hashear(texto: String): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(texto.toByteArray())
        return bytes.joinToString("") { byte -> "%02x".format(byte) }
    }
}