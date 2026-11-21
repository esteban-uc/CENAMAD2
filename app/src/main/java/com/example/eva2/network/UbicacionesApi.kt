package com.example.eva2.network

import com.example.eva2.model.UbicacionDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HEAD
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface UbicacionesApi {

    @POST("ubicaciones")
    suspend fun crearUbicacion(@Body ubicacion: UbicacionDto): Response<UbicacionDto>

    @GET("ubicaciones")
    suspend fun obtenerUbicaciones(): Response<List<UbicacionDto>>

    @PATCH("ubicaciones/{ubicacion_id}")
    suspend fun actualizarParcialUbicacion(
        @Path("ubicacion_id") ubicacionId: Int,
        @Body ubicacion: UbicacionDto
    ): Response<UbicacionDto>

    @PUT("ubicaciones/{ubicacion_id}")
    suspend fun reemplazarUbicacion(
        @Path("ubicacion_id") ubicacionId: Int,
        @Body ubicacion: UbicacionDto
    ): Response<UbicacionDto>

    @HEAD("ubicaciones/{ubicacion_id}")
    suspend fun verificarUbicacion(
        @Path("ubicacion_id") ubicacionId: Int
    ): Response<Void>

    @DELETE("ubicaciones/{ubicacion_id}")
    suspend fun eliminarUbicacion(
        @Path("ubicacion_id") ubicacionId: Int
    ): Response<Unit>
}