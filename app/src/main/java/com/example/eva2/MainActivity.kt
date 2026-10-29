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

    // la base de datos se crea recién cuando se usa por primera vez
    private val baseDeDatos by lazy {
        Room.databaseBuilder(applicationContext, AppDatabase::class.java, "cenamad_db")
            .addCallback(DatosIniciales)
            // si cambiamos las tablas mientras desarrollamos, se borra y se crea de nuevo
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    // un solo repositorio para toda la app, así login, registro y perfil comparten la sesión
    private val usuarioRepository by lazy {
        UsuarioRepository(baseDeDatos.usuarioDao(), baseDeDatos.sesionDao())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Eva2Theme {
                // NAVEGACIÓN: toda la navegación está en ui/Navegacion.kt
                Navegacion(usuarioRepository = usuarioRepository)
            }
        }
    }
}
 