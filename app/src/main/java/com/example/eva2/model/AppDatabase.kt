package com.example.eva2.model

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [Usuario::class, Sesion::class], version = 3, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun usuarioDao(): UsuarioDao
    abstract fun sesionDao(): SesionDao
}
