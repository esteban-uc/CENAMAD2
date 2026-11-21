package com.example.eva2.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.eva2.model.Ubicacion
import com.example.eva2.model.UbicacionDto
import com.example.eva2.network.RetrofitClient
import com.google.android.gms.location.LocationServices

class UbicacionRepository(context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    private val api = RetrofitClient.ubicacionesApi

    fun obtenerCenamad(): Ubicacion = Ubicacion(
        nombre = "CENAMAD",
        direccion = "Av. Vicuña Mackenna 4860",
        comuna = "Macul",
        ciudad = "Santiago, Chile",
        latitud = -33.4986,
        longitud = -70.6120,
        referencia = "Campus San Joaquín UC",
        descripcion = "Centro Nacional para la Industria de la Madera, liderado por CIM UC"
    )

    fun obtenerLugaresCercanos(): List<Ubicacion> = listOf(
        Ubicacion(
            nombre = "Metro San Joaquín",
            direccion = "Av. Vicuña Mackenna",
            comuna = "San Joaquín",
            ciudad = "Santiago, Chile",
            latitud = -33.4994,
            longitud = -70.6164,
            descripcion = "Línea 5, estación más cercana al campus"
        )
    )

    @SuppressLint("MissingPermission")
    fun obtenerMiUbicacion(onResultado: (Ubicacion?) -> Unit) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location ->
                if (location == null) {
                    onResultado(null)
                } else {
                    onResultado(
                        Ubicacion(
                            nombre = "Tú estás aquí",
                            direccion = "",
                            comuna = "",
                            ciudad = "",
                            latitud = location.latitude,
                            longitud = location.longitude
                        )
                    )
                }
            }
            .addOnFailureListener {
                onResultado(null)
            }
    }

    suspend fun obtenerUbicacionesRemotas(): Result<List<UbicacionDto>> {
        return try {
            val response = api.obtenerUbicaciones()
            if (response.isSuccessful) {
                Result.success(response.body() ?: emptyList())
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun crearUbicacionRemota(ubicacion: UbicacionDto): Result<UbicacionDto> {
        return try {
            val response = api.crearUbicacion(ubicacion)
            val creada = response.body()
            if (response.isSuccessful && creada != null) {
                Result.success(creada)
            } else {
                Result.failure(
                    Exception("Error HTTP ${response.code()}: respuesta vacía o inválida de Xano")
                )
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun actualizarParcialUbicacionRemota(id: Int, ubicacion: UbicacionDto): Result<UbicacionDto> {
        return try {
            val response = api.actualizarParcialUbicacion(id, ubicacion)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun reemplazarUbicacionRemota(id: Int, ubicacion: UbicacionDto): Result<UbicacionDto> {
        return try {
            val response = api.reemplazarUbicacion(id, ubicacion)
            if (response.isSuccessful && response.body() != null) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun verificarUbicacionRemota(id: Int): Result<Boolean> {
        return try {
            val response = api.verificarUbicacion(id)
            Result.success(response.isSuccessful)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun eliminarUbicacionRemota(id: Int): Result<Unit> {
        return try {
            val response = api.eliminarUbicacion(id)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Error HTTP ${response.code()}: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}