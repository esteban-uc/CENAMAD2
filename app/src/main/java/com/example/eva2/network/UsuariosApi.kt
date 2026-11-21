package com.example.eva2.network

import com.example.eva2.model.UsuarioDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UsuariosApi {

    @POST("usuarios")
    suspend fun crearUsuario(@Body usuario: UsuarioDto): Response<UsuarioDto>

    @GET("usuarios")
    suspend fun obtenerUsuarios(): Response<List<UsuarioDto>>

    @PATCH("usuarios/{usuario_id}")
    suspend fun actualizarParcialUsuario(
        @Path("usuario_id") usuarioId: Int,
        @Body usuario: UsuarioDto
    ): Response<UsuarioDto>

    @PUT("usuarios/{usuario_id}")
    suspend fun reemplazarUsuario(
        @Path("usuario_id") usuarioId: Int,
        @Body usuario: UsuarioDto
    ): Response<UsuarioDto>

    @HEAD("usuarios/{usuario_id}")
    suspend fun verificarUsuario(
        @Path("usuario_id") usuarioId: Int
    ): Response<Void>

    @DELETE("usuarios/{usuario_id}")
    suspend fun eliminarUsuario(
        @Path("usuario_id") usuarioId: Int
    ): Response<Unit>
}