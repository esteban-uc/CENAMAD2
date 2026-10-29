package com.example.eva2.ui


import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.eva2.R
import com.example.eva2.model.Ubicacion
import com.example.eva2.viewmodel.UbicacionViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.compose.*


@Composable
fun UbicacionScreen(
    viewModel: UbicacionViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val cenamad = uiState.cenamad
    val posicionCenamad = LatLng(cenamad.latitud, cenamad.longitud)


    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posicionCenamad, 16f)
    }


    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val permisoConcedido = permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (permisoConcedido) viewModel.buscarMiUbicacion() else viewModel.permisoDenegado()
    }


    LaunchedEffect(uiState.miUbicacion) {
        val ubicacionUsuario = uiState.miUbicacion ?: return@LaunchedEffect
        val limitesDelMapa = LatLngBounds.builder()
            .include(posicionCenamad)
            .include(LatLng(ubicacionUsuario.latitud, ubicacionUsuario.longitud))
            .build()
        cameraPositionState.move(CameraUpdateFactory.newLatLngBounds(limitesDelMapa, 120))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {

            Image(
                painter = painterResource(id = R.drawable.logo_cenamad),
                contentDescription = "Logo de CENAMAD",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(140.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
            )
            Text(
                text = "ENCUÉNTRANOS",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Nuestra ubicación",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = cenamad.descripcion,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = cenamad.direccionCompleta,
                style = MaterialTheme.typography.bodyLarge
            )
            Text(
                text = cenamad.referencia,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp)
        ) {
            GoogleMap(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp),
                cameraPositionState = cameraPositionState
            ) {

                val estadoMarcadorCenamad = remember(posicionCenamad) {
                    MarkerState(position = posicionCenamad)
                }
                Marker(
                    state = estadoMarcadorCenamad,
                    title = cenamad.nombre,
                    snippet = cenamad.direccion
                )


                uiState.lugaresCercanos.forEach { lugarCercano ->
                    val estadoMarcadorLugar = remember(lugarCercano) {
                        MarkerState(position = LatLng(lugarCercano.latitud, lugarCercano.longitud))
                    }
                    Marker(
                        state = estadoMarcadorLugar,
                        title = lugarCercano.nombre,
                        snippet = lugarCercano.descripcion,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }


                uiState.miUbicacion?.let { ubicacionUsuario ->
                    val estadoMarcadorUsuario = remember(ubicacionUsuario) {
                        MarkerState(position = LatLng(ubicacionUsuario.latitud, ubicacionUsuario.longitud))
                    }
                    Marker(
                        state = estadoMarcadorUsuario,
                        title = ubicacionUsuario.nombre,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                }
            }
        }

        uiState.distanciaTexto?.let { distanciaTexto ->
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = distanciaTexto,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        Button(
            onClick = { abrirEnGoogleMaps(context, cenamad) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Abrir en Google Maps")
        }

        OutlinedButton(
            onClick = {
                permisoLauncher.launch(
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            },
            enabled = !uiState.cargandoMiUbicacion,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(if (uiState.cargandoMiUbicacion) "Buscando tu ubicación..." else "¿A qué distancia estoy?")
        }

        uiState.mensajeError?.let { mensajeError ->
            Text(text = mensajeError, color = MaterialTheme.colorScheme.error)
        }
    }
}


private fun abrirEnGoogleMaps(context: Context, lugar: Ubicacion) {
    val textoBusqueda = Uri.encode(lugar.direccionCompleta)
    val enlaceMaps = Uri.parse("https://www.google.com/maps/search/?api=1&query=$textoBusqueda")
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, enlaceMaps))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No hay una app para abrir mapas", Toast.LENGTH_SHORT).show()
    }
}