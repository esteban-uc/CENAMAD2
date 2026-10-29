package com.example.eva2.ui.usuario

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import com.example.eva2.ui.nav.Iconos
import com.example.eva2.ui.nav.NavColores

@Composable
fun DialogoCerrarSesion(
    onConfirmar: () -> Unit,
    onCancelar: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onCancelar,
        icon = { Icon(imageVector = Iconos.CerrarSesion, contentDescription = null, tint = NavColores.Burdeo) },
        title = { Text(text = "¿Cerrar sesión?") },
        text = { Text(text = "Tendrás que ingresar tu correo y contraseña para volver a entrar.") },
        confirmButton = {
            TextButton(onClick = onConfirmar) {
                Text(text = "Cerrar sesión", color = NavColores.Burdeo, fontWeight = FontWeight.SemiBold)
            }
        },
        dismissButton = {
            TextButton(onClick = onCancelar) {
                Text(text = "Cancelar", color = NavColores.TextoGris)
            }
        },
        containerColor = NavColores.Crema
    )
}
