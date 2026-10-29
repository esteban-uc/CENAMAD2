package com.example.eva2.ui.usuario

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.Iconos
import com.example.eva2.ui.nav.NavColores

@Composable
fun LogoAcceso(
    tamano: Dp = 88.dp,
    colorFondo: Color = NavColores.Burdeo,
    colorLetra: Color = NavColores.Blanco
) {
    Box(
        modifier = Modifier
            .size(tamano)
            .clip(CircleShape)
            .background(colorFondo),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "C",
            color = colorLetra,
            fontFamily = FontFamily.Serif,
            fontWeight = FontWeight.Bold,
            fontSize = (tamano.value * 0.52f).sp
        )
    }
}

@Composable
fun EncabezadoMadera(etiqueta: String, titulo: String, descripcion: String) {
    val aparicion = remember { MutableTransitionState(false).apply { targetState = true } }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(NavColores.Burdeo)
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        // logo: círculo blanco con la "C" burdeo + nombre
        Row(verticalAlignment = Alignment.CenterVertically) {
            LogoAcceso(tamano = 36.dp, colorFondo = NavColores.Blanco, colorLetra = NavColores.Burdeo)
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "CENAMAD",
                color = NavColores.Blanco,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                letterSpacing = 3.sp
            )
        }

        Spacer(modifier = Modifier.height(44.dp))

        AnimatedVisibility(
            visibleState = aparicion,
            enter = fadeIn(tween(durationMillis = 700)) +
                    slideInVertically(tween(durationMillis = 700)) { altura -> altura / 4 }
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(1.dp)
                            .background(NavColores.RosaClaro)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = etiqueta.uppercase(),
                        color = NavColores.RosaClaro,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 2.sp
                    )
                }
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = titulo,
                    color = NavColores.Blanco,
                    fontFamily = FontFamily.Serif,
                    fontSize = 36.sp,
                    lineHeight = 40.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text(
                    text = descripcion,
                    color = NavColores.Blanco.copy(alpha = 0.85f),
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }
        }
    }
}

@Composable
fun EtiquetaSeccion(texto: String) {
    Text(
        text = texto.uppercase(),
        color = NavColores.Burdeo,
        fontSize = 12.sp,
        fontWeight = FontWeight.SemiBold,
        letterSpacing = 2.sp
    )
}

@Composable
fun TituloSerif(texto: String) {
    Text(
        text = texto,
        color = NavColores.TextoOscuro,
        fontFamily = FontFamily.Serif,
        fontSize = 32.sp,
        lineHeight = 38.sp
    )
}

@Composable
fun CampoFormulario(
    valor: String,
    alCambiar: (String) -> Unit,
    etiqueta: String,
    ejemplo: String,
    icono: ImageVector,
    error: String?,
    ayuda: String? = null,
    tipoTeclado: KeyboardType = KeyboardType.Text,
    esContrasena: Boolean = false,
    esUltimoCampo: Boolean = false,
    alTerminar: () -> Unit = {}
) {
    var contrasenaVisible by rememberSaveable { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = etiqueta,
            color = NavColores.TextoOscuro,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = valor,
            onValueChange = alCambiar,
            placeholder = { Text(text = ejemplo, color = NavColores.TextoGris) },
            leadingIcon = { Icon(imageVector = icono, contentDescription = null) },
            trailingIcon = {
                if (esContrasena) {
                    IconButton(onClick = { contrasenaVisible = !contrasenaVisible }) {
                        Icon(
                            imageVector = if (contrasenaVisible) Iconos.OjoTachado else Iconos.Ojo,
                            contentDescription = if (contrasenaVisible) "Ocultar contraseña" else "Mostrar contraseña"
                        )
                    }
                } else if (error != null) {
                    Icon(imageVector = Iconos.Error, contentDescription = "Error", tint = MaterialTheme.colorScheme.error)
                }
            },
            isError = error != null,
            supportingText = {
                when {
                    error != null -> MensajeError(error)
                    ayuda != null -> Text(text = ayuda, color = NavColores.TextoGris)
                }
            },
            visualTransformation =
                if (esContrasena && !contrasenaVisible) PasswordVisualTransformation() else VisualTransformation.None,
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = tipoTeclado,
                imeAction = if (esUltimoCampo) ImeAction.Done else ImeAction.Next
            ),
            keyboardActions = KeyboardActions(onDone = { alTerminar() }),
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = NavColores.Burdeo,
                unfocusedBorderColor = NavColores.Linea,
                cursorColor = NavColores.Burdeo,
                focusedLeadingIconColor = NavColores.Burdeo,
                unfocusedLeadingIconColor = NavColores.TextoGris,
                focusedTrailingIconColor = NavColores.TextoGris,
                unfocusedTrailingIconColor = NavColores.TextoGris,
                focusedContainerColor = NavColores.Blanco,
                unfocusedContainerColor = NavColores.Blanco,
                errorContainerColor = NavColores.Blanco
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Composable
private fun MensajeError(mensaje: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Iconos.Error,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.error,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(text = mensaje)
    }
}

@Composable
fun BotonConCarga(texto: String, cargando: Boolean, alPresionar: () -> Unit) {
    Button(
        onClick = alPresionar,
        enabled = !cargando,
        shape = RoundedCornerShape(2.dp),
        colors = ButtonDefaults.buttonColors(containerColor = NavColores.Burdeo),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        if (cargando) {
            CircularProgressIndicator(
                modifier = Modifier.size(22.dp),
                strokeWidth = 2.dp,
                color = NavColores.Blanco
            )
        } else {
            Text(text = texto, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.width(12.dp))
            Icon(imageVector = Iconos.Flecha, contentDescription = null, modifier = Modifier.size(20.dp))
        }
    }
}

@Composable
fun BotonSecundario(texto: String, alPresionar: () -> Unit) {
    OutlinedButton(
        onClick = alPresionar,
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, NavColores.Linea),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = NavColores.Blanco.copy(alpha = 0.4f),
            contentColor = NavColores.TextoOscuro
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text = texto, fontSize = 15.sp, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
fun SeparadorO() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = NavColores.Linea)
        Text(
            text = "o",
            color = NavColores.TextoGris,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 12.dp)
        )
        HorizontalDivider(modifier = Modifier.weight(1f), color = NavColores.Linea)
    }
}