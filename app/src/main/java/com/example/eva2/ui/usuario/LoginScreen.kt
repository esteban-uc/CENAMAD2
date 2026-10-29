package com.example.eva2.ui.usuario

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.Iconos
import com.example.eva2.ui.nav.NavColores
import com.example.eva2.viewmodel.LoginViewModel

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onIngresar: () -> Unit,
    onIrARegistro: () -> Unit,
    onContinuarComoVisitante: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()

    LaunchedEffect(estado.ingresoExitoso) {
        if (estado.ingresoExitoso) onIngresar()
    }

    val desplazamientoSacudida = remember { Animatable(0f) }

    LaunchedEffect(estado.intentosFallidos) {
        if (estado.intentosFallidos > 0) {
            repeat(3) {
                desplazamientoSacudida.animateTo(12f, tween(durationMillis = 50))
                desplazamientoSacudida.animateTo(-12f, tween(durationMillis = 50))
            }
            desplazamientoSacudida.animateTo(0f, tween(durationMillis = 50))
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavColores.Crema)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoMadera(
            etiqueta = "Conocimiento abierto",
            titulo = "Bienvenido a tu biblioteca de la madera.",
            descripcion = "Accede a investigación y conocimiento para transformar la industria en Chile."
        )

        Column(modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp)) {
            EtiquetaSeccion(texto = "Mi CENAMAD")
            Spacer(modifier = Modifier.height(6.dp))
            TituloSerif(texto = "Inicia sesión")
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Guarda publicaciones y continúa tus lecturas desde cualquier lugar.",
                color = NavColores.TextoGris,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.offset {
                    IntOffset(x = desplazamientoSacudida.value.dp.roundToPx(), y = 0)
                }
            ) {
                CampoFormulario(
                    valor = estado.correo,
                    alCambiar = viewModel::alCambiarCorreo,
                    etiqueta = "Correo electrónico",
                    ejemplo = "nombre@gmail.com",
                    icono = Iconos.Correo,
                    error = estado.errorCorreo,
                    tipoTeclado = KeyboardType.Email
                )
                CampoFormulario(
                    valor = estado.contrasena,
                    alCambiar = viewModel::alCambiarContrasena,
                    etiqueta = "Contraseña",
                    ejemplo = "Tu contraseña",
                    icono = Iconos.Candado,
                    error = estado.errorContrasena,
                    tipoTeclado = KeyboardType.Password,
                    esContrasena = true,
                    esUltimoCampo = true,
                    alTerminar = viewModel::iniciarSesion
                )

                AnimatedVisibility(
                    visible = estado.errorGeneral != null,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    Card(
                        shape = RoundedCornerShape(4.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.errorContainer),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(12.dp)
                        ) {
                            Icon(
                                imageVector = Iconos.Advertencia,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onErrorContainer
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = estado.errorGeneral.orEmpty(),
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            BotonConCarga(
                texto = "Ingresar",
                cargando = estado.cargando,
                alPresionar = viewModel::iniciarSesion
            )

            SeparadorO()

            BotonSecundario(texto = "Continuar como visitante", alPresionar = onContinuarComoVisitante)

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "¿No tienes cuenta?", color = NavColores.TextoGris, fontSize = 14.sp)
                TextButton(onClick = onIrARegistro) {
                    Text(text = "Crea una aquí", color = NavColores.Burdeo, fontWeight = FontWeight.SemiBold)
                }
            }

            Text(
                text = "Cuenta de prueba: demo@gmail.com · Demo1234",
                color = NavColores.TextoGris,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}
