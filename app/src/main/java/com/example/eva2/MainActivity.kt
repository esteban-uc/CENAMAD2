package com.example.eva2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.room.Room
import com.example.eva2.model.AppDatabase
import com.example.eva2.model.DatosIniciales
import com.example.eva2.repository.UsuarioRepository
import com.example.eva2.ui.Navegacion
import com.example.eva2.ui.theme.Eva2Theme

class MainActivity : ComponentActivity() {

    private val baseDeDatos by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cenamad_db")
            .addCallback(DatosIniciales)
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }
    private val usuarioRepository by lazy {
        UsuarioRepository(baseDeDatos.usuarioDao(), baseDeDatos.sesionDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Eva2Theme {
                Navegacion(usuarioRepository = usuarioRepository)
            }
        }
    }
}
 