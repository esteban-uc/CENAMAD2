package com.example.eva2.ui.usuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.eva2.ui.nav.Iconos
import com.example.eva2.ui.nav.NavColores
import com.example.eva2.viewmodel.RegistroViewModel

@Composable
fun RegistroScreen(
    viewModel: RegistroViewModel,
    onRegistrado: () -> Unit,
    onVolver: () -> Unit
) {
    val estado by viewModel.estado.collectAsState()

    LaunchedEffect(estado.registroExitoso) {
        if (estado.registroExitoso) onRegistrado()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavColores.Crema)
            .verticalScroll(rememberScrollState())
    ) {
        EncabezadoMadera(
            etiqueta = "Únete a CENAMAD",
            titulo = "Crea tu cuenta.",
            descripcion = "Guarda publicaciones y sigue el trabajo de los investigadores de la madera."
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.padding(horizontal = 24.dp, vertical = 28.dp)
        ) {
            EtiquetaSeccion(texto = "Mi CENAMAD")
            TituloSerif(texto = "Tus datos")
            Spacer(modifier = Modifier.height(16.dp))

            CampoFormulario(
                valor = estado.nombre,
                alCambiar = viewModel::alCambiarNombre,
                etiqueta = "Nombre completo",
                ejemplo = "Ej: Camila Rojas",
                icono = Iconos.Perfil,
                error = estado.errorNombre
            )
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
                ejemplo = "Mínimo 8 caracteres",
                icono = Iconos.Candado,
                error = estado.errorContrasena,
                ayuda = "Debe incluir una mayúscula y un número",
                tipoTeclado = KeyboardType.Password,
                esContrasena = true
            )
            CampoFormulario(
                valor = estado.confirmacion,
                alCambiar = viewModel::alCambiarConfirmacion,
                etiqueta = "Confirmar contraseña",
                ejemplo = "Repite tu contraseña",
                icono = Iconos.Candado,
                error = estado.errorConfirmacion,
                tipoTeclado = KeyboardType.Password,
                esContrasena = true,
                esUltimoCampo = true,
                alTerminar = viewModel::registrar
            )

            Spacer(modifier = Modifier.height(12.dp))

            BotonConCarga(
                texto = "Crear cuenta",
                cargando = estado.cargando,
                alPresionar = viewModel::registrar
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "¿Ya tienes cuenta?", color = NavColores.TextoGris, fontSize = 14.sp)
                TextButton(onClick = onVolver) {
                    Text(text = "Inicia sesión", color = NavColores.Burdeo, fontWeight = FontWeight.SemiBold)
                }
            }
        }
    }
}
