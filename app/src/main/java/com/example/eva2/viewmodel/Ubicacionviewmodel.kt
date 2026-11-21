package com.example.eva2.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.eva2.model.Ubicacion
import com.example.eva2.model.UbicacionDto
import com.example.eva2.repository.UbicacionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.Locale
import kotlin.math.roundToInt

data class UbicacionUiState(
    val cenamad: Ubicacion,
    val lugaresCercanos: List<Ubicacion> = emptyList(),
    val ubicacionesRemotas: List<UbicacionDto> = emptyList(),
    val miUbicacion: Ubicacion? = null,
    val distanciaTexto: String? = null,
    val cargandoMiUbicacion: Boolean = false,
    val cargandoRemotas: Boolean = false,
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

    init {
        cargarUbicacionesRemotas()
    }

    fun cargarUbicacionesRemotas() {
        _uiState.update { it.copy(cargandoRemotas = true, mensajeError = null) }

        viewModelScope.launch {
            val resultado = repository.obtenerUbicacionesRemotas()
            _uiState.update { estadoActual ->
                resultado.fold(
                    onSuccess = { lista ->
                        estadoActual.copy(
                            ubicacionesRemotas = lista,
                            cargandoRemotas = false
                        )
                    },
                    onFailure = { error ->
                        estadoActual.copy(
                            cargandoRemotas = false,
                            mensajeError = error.message ?: "Error al cargar ubicaciones remotas"
                        )
                    }
                )
            }
        }
    }

    fun guardarUbicacionEnXano(ubicacionBase: Ubicacion) {
        _uiState.update { it.copy(cargandoRemotas = true, mensajeError = null) }

        viewModelScope.launch {
            val nombreRegistro = ubicacionBase.nombre.ifBlank { "Ubicación guardada" }

            val dto = UbicacionDto(
                nombre = nombreRegistro,
                direccion = ubicacionBase.direccion,
                comuna = ubicacionBase.comuna,
                ciudad = ubicacionBase.ciudad,
                latitud = ubicacionBase.latitud,
                longitud = ubicacionBase.longitud,
                referencia = ubicacionBase.referencia,
                descripcion = ubicacionBase.descripcion
            )

            val resultado = repository.crearUbicacionRemota(dto)
            resultado.fold(
                onSuccess = {
                    cargarUbicacionesRemotas()
                },
                onFailure = { error ->
                    _uiState.update { estadoActual ->
                        estadoActual.copy(
                            cargandoRemotas = false,
                            mensajeError = error.message ?: "Error al guardar ubicación en Xano"
                        )
                    }
                }
            )
        }
    }

    fun guardarUbicacionManualEnXano(
        nombre: String,
        latitudTexto: String,
        longitudTexto: String,
        direccion: String = "",
        comuna: String = ""
    ) {
        val nombreLimpio = nombre.trim().ifBlank { "Ubicación manual" }
        val lat = latitudTexto.trim().replace(",", ".").toDoubleOrNull()
        val lon = longitudTexto.trim().replace(",", ".").toDoubleOrNull()

        if (lat == null || lon == null) {
            _uiState.update {
                it.copy(mensajeError = "Ingresa latitud y longitud válidas (ej. -33.4986).")
            }
            return
        }

        _uiState.update { it.copy(cargandoRemotas = true, mensajeError = null) }

        viewModelScope.launch {
            val dto = UbicacionDto(
                nombre = nombreLimpio,
                direccion = direccion.trim(),
                comuna = comuna.trim(),
                ciudad = "Santiago, Chile",
                latitud = lat,
                longitud = lon,
                referencia = "",
                descripcion = "Creada manualmente desde la app"
            )

            val resultado = repository.crearUbicacionRemota(dto)
            resultado.fold(
                onSuccess = {
                    cargarUbicacionesRemotas()
                },
                onFailure = { error ->
                    _uiState.update { estadoActual ->
                        estadoActual.copy(
                            cargandoRemotas = false,
                            mensajeError = error.message ?: "Error al guardar ubicación manual"
                        )
                    }
                }
            )
        }
    }

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