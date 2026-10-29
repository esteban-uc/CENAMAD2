package com.example.eva2.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sesion")
data class Sesion(
    @PrimaryKey val id: Int = 1,
    val usuarioId: Int
)
