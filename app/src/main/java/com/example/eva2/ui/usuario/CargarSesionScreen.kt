package com.example.eva2.ui.usuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.eva2.ui.nav.NavColores

@Composable
fun CargaSesionScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(NavColores.Crema),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LogoAcceso()
        Spacer(modifier = Modifier.height(24.dp))
        CircularProgressIndicator(color = NavColores.Burdeo)
    }
}