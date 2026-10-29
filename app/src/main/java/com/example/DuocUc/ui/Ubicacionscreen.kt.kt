package com.example.DuocUc.ui


import android.Manifest
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.DuocUc.R
import com.example.DuocUc.model.Ubicacion
import com.example.DuocUc.viewmodel.UbicacionViewModel
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

    // La cámara del mapa parte centrada en CENAMAD
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(posicionCenamad, 16f)
    }

    // ANIMACIÓN 1: el encabezado aparece deslizándose al abrir la pantalla
    val estadoAparicionEncabezado = remember {
        MutableTransitionState(false).apply { targetState = true }
    }

    // GPS: pide el permiso de ubicación; si lo aceptan, el ViewModel busca la ubicación
    val permisoLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permisos ->
        val permisoConcedido = permisos[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permisos[Manifest.permission.ACCESS_COARSE_LOCATION] == true

        if (permisoConcedido) viewModel.buscarMiUbicacion() else viewModel.permisoDenegado()
    }

    // Cuando llega tu ubicación, el mapa se aleja para mostrarte a ti y a CENAMAD
    LaunchedEffect(uiState.miUbicacion) {
        val ubicacionUsuario = uiState.miUbicacion ?: return@LaunchedEffect
        val limitesDelMapa = LatLngBounds.builder()
            .include(posicionCenamad)
            .include(LatLng(ubicacionUsuario.latitud, ubicacionUsuario.longitud))
            .build()
        cameraPositionState.animate(CameraUpdateFactory.newLatLngBounds(limitesDelMapa, 120))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        AnimatedVisibility(
            visibleState = estadoAparicionEncabezado,
            enter = fadeIn(tween(durationMillis = 700)) +
                    slideInVertically(tween(durationMillis = 700)) { alturaTotal -> alturaTotal / 4 }
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                // IMAGEN: ilustración del edificio (res/drawable/img_cenamad_campus.xml)
                Image(
                    painter = painterResource(id = R.drawable.img_cenamad_campus),
                    contentDescription = "Ilustración del edificio de CENAMAD",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(160.dp)
                        .clip(RoundedCornerShape(12.dp))
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
                // Marcador rojo: CENAMAD
                Marker(
                    state = MarkerState(position = posicionCenamad),
                    title = cenamad.nombre,
                    snippet = cenamad.direccion
                )

                // Marcadores azules: lugares de referencia (metro)
                uiState.lugaresCercanos.forEach { lugarCercano ->
                    Marker(
                        state = MarkerState(position = LatLng(lugarCercano.latitud, lugarCercano.longitud)),
                        title = lugarCercano.nombre,
                        snippet = lugarCercano.descripcion,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }

                // Marcador verde: el usuario (solo si ya se obtuvo)
                uiState.miUbicacion?.let { ubicacionUsuario ->
                    Marker(
                        state = MarkerState(position = LatLng(ubicacionUsuario.latitud, ubicacionUsuario.longitud)),
                        title = ubicacionUsuario.nombre,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
                    )
                }
            }
        }

        // ANIMACIÓN 2: la tarjeta de distancia se despliega cuando el GPS responde
        AnimatedVisibility(
            visible = uiState.distanciaTexto != null,
            enter = fadeIn(tween(durationMillis = 400)) + expandVertically(tween(durationMillis = 400))
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Text(
                    text = uiState.distanciaTexto.orEmpty(),
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

// Abre la app de Google Maps (o el navegador) buscando la dirección exacta de CENAMAD
private fun abrirEnGoogleMaps(context: Context, lugar: Ubicacion) {
    val textoBusqueda = Uri.encode(lugar.direccionCompleta)
    val enlaceMaps = Uri.parse("https://www.google.com/maps/search/?api=1&query=$textoBusqueda")
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, enlaceMaps))
    } catch (e: ActivityNotFoundException) {
        Toast.makeText(context, "No hay una app para abrir mapas", Toast.LENGTH_SHORT).show()
    }
}
