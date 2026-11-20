package com.example.eva2.viewmodel


import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import com.example.eva2.model.Ubicacion
import com.example.eva2.repository.UbicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import kotlin.math.roundToInt


data class UbicacionUiState(
    val cenamad: Ubicacion,
    val lugaresCercanos: List<Ubicacion> = emptyList(),
    val miUbicacion: Ubicacion? = null,
    val distanciaTexto: String? = null,
    val cargandoMiUbicacion: Boolean = false,
    val mensajeError: String? = null
)

class UbicacionViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = UbicacionRepository(application)


    private val _uiState = MutableStateFlow(
        UbicacionUiState(
            cenamad = repository.obtenerCenamad(),
            lugaresCercanos = repository.obtenerLugaresCercanos()
        )
    )


    val uiState: StateFlow<UbicacionUiState> = _uiState.asStateFlow()

    fun buscarMiUbicacion() {
        _uiState.update { it.copy(cargandoMiUbicacion = true, mensajeError = null) }

        repository.obtenerMiUbicacion { ubicacionUsuario ->
            _uiState.update { estadoActual ->
                if (ubicacionUsuario == null) {
                    estadoActual.copy(
                        cargandoMiUbicacion = false,
                        mensajeError = "No se pudo obtener tu ubicación. Activa el GPS e intenta de nuevo."
                    )
                } else {
                    estadoActual.copy(
                        miUbicacion = ubicacionUsuario,
                        distanciaTexto = calcularDistanciaTexto(ubicacionUsuario, estadoActual.cenamad),
                        cargandoMiUbicacion = false
                    )
                }
            }
        }
    }

    fun permisoDenegado() {
        _uiState.update {
            it.copy(mensajeError = "Sin permiso de ubicación no podemos mostrar dónde estás.")
        }
    }

    private fun calcularDistanciaTexto(origen: Ubicacion, destino: Ubicacion): String {
        val resultadoEnMetros = FloatArray(1)
        Location.distanceBetween(
            origen.latitud, origen.longitud,
            destino.latitud, destino.longitud,
            resultadoEnMetros
        )
        val metros = resultadoEnMetros[0]

        val distanciaFormateada = if (metros < 1000) {
            "${metros.roundToInt()} m"
        } else {
            String.format(Locale.forLanguageTag("es-CL"), "%.1f km", metros / 1000)
        }
        return "Estás a $distanciaFormateada de ${destino.nombre}"
    }
}
