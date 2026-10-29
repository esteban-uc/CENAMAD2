package com.example.DuocUc.repository

import android.annotation.SuppressLint
import android.content.Context
import com.example.DuocUc.model.Ubicacion
import com.google.android.gms.location.LocationServices

/**
 * REPOSITORY
 * Es el único lugar que sabe DE DÓNDE salen los datos.
 * - Los datos de CENAMAD son fijos (están escritos aquí).
 * - La ubicación del usuario viene del GPS (FusedLocationProviderClient).
 * El ViewModel le pide los datos a esta clase y no le importa cómo se obtienen.
 */
class UbicacionRepository(context: Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    // Dirección oficial: CENAMAD funciona en el Campus San Joaquín de la UC
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

    // Lugares de referencia que ayudan a llegar
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

    // Pide la última ubicación conocida del teléfono.
    // El permiso se pide en la pantalla ANTES de llamar a esta función.
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
