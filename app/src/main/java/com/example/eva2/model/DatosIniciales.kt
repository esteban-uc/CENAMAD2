package com.example.eva2.model

import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase


object DatosIniciales : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        insertarUsuarioDemo(db)
    }
    override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
        super.onDestructiveMigration(db)
        insertarUsuarioDemo(db)
    }
    private fun insertarUsuarioDemo(db: SupportSQLiteDatabase) {
        db.execSQL(
            """
            INSERT INTO usuarios (nombre, correo, contrasenaHash)
            VALUES ('Usuario Demo', 'demo@gmail.com',
                    'b22f213ec710f0b0e86297d10279d69171f50f01a04edf40f472a563e7ad8576')
            """.trimIndent()
        )
    }
}
