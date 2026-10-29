package com.example.eva2.ui.usuario

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.Iconos
import com.example.eva2.ui.nav.NavColores
import com.example.eva2.viewmodel.PerfilViewModel

@Composable
fun PerfilUsuarioScreen(
    viewModel: PerfilViewModel,
    onIniciarSesion: () -> Unit,
    onVerGuardados: () -> Unit,
    onVerIndicadores: () -> Unit,
    onAcercaDe: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val usuario by viewModel.usuario.collectAsState()
    val esVisitante = usuario == null

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(NavColores.Crema)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 32.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
                .background(NavColores.RosaClaro)
        ) {
            val usuarioActual = usuario
            if (usuarioActual != null) {
                Text(
                    text = usuarioActual.nombre.first().uppercase(),
                    color = NavColores.Burdeo,
                    fontFamily = FontFamily.Serif,
                    fontSize = 40.sp
                )
            } else {
                Icon(
                    imageVector = Iconos.Perfil,
                    contentDescription = null,
                    tint = NavColores.Burdeo,
                    modifier = Modifier.size(40.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        EtiquetaSeccion(texto = "Mi CENAMAD")
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = if (esVisitante) "Hola, visitante" else "Hola, ${usuario?.nombre?.substringBefore(" ")}",
            color = NavColores.TextoOscuro,
            fontFamily = FontFamily.Serif,
            fontSize = 36.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = usuario?.correo ?: "Inicia sesión para sincronizar tu biblioteca.",
            color = NavColores.TextoGris,
            fontSize = 14.sp,
            textAlign = TextAlign.Center
        )

        if (esVisitante) {
            Spacer(modifier = Modifier.height(24.dp))
            BotonConCarga(texto = "Iniciar sesión", cargando = false, alPresionar = onIniciarSesion)
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = NavColores.Linea)

        FilaPerfil(
            icono = Iconos.Guardados,
            titulo = "Publicaciones guardadas",
            subtitulo = "Tus lecturas marcadas",
            alPresionar = onVerGuardados
        )
        FilaPerfil(
            icono = Iconos.Indicadores,
            titulo = "Indicadores",
            subtitulo = "Cifras del centro",
            alPresionar = onVerIndicadores
        )
        FilaPerfil(
            icono = Iconos.Info,
            titulo = "Acerca de CENAMAD",
            subtitulo = "Centro Nacional de Excelencia",
            alPresionar = onAcercaDe
        )

        if (!esVisitante) {
            Spacer(modifier = Modifier.height(32.dp))
            BotonSecundario(texto = "Cerrar sesión", alPresionar = onCerrarSesion)
        }
    }
}

@Composable
private fun FilaPerfil(
    icono: ImageVector,
    titulo: String,
    subtitulo: String,
    alPresionar: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = alPresionar)
            .padding(vertical = 16.dp)
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(44.dp)
                .clip(CircleShape)
                .background(NavColores.RosaClaro.copy(alpha = 0.6f))
        ) {
            Icon(
                imageVector = icono,
                contentDescription = null,
                tint = NavColores.Burdeo,
                modifier = Modifier.size(22.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = titulo, color = NavColores.TextoOscuro, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = subtitulo, color = NavColores.TextoGris, fontSize = 12.sp)
        }
        Icon(imageVector = Iconos.Chevron, contentDescription = null, tint = NavColores.TextoOscuro)
    }
    HorizontalDivider(color = NavColores.Linea)
}
