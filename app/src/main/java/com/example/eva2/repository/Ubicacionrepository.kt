package com.example.eva2.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.eva2.model.Ubicacion
import com.google.android.gms.location.LocationServices


class UbicacionRepository(context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

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
}
