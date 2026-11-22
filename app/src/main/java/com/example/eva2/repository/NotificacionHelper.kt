package com.example.eva2.repository

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.eva2.MainActivity
import com.example.eva2.R

/* Gestiona la creación del canal y el envío de notificaciones locales de CENAMAD*/

object NotificacionHelper {

    private const val CANAL_ID = "cenamad_alertas"
    private const val CANAL_NOMBRE = "Alertas de Investigación CENAMAD"
    private const val CANAL_DESCRIPCION = "Avisos sobre proyectos, publicaciones y actividad de sesión"

    /* Registra el canal de notificaciones requerido en Android 8.0 (API 26) o superior*/

    fun crearCanal(contexto: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL_ID,
                CANAL_NOMBRE,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = CANAL_DESCRIPCION
            }
            val gestor = contexto.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            gestor.createNotificationChannel(canal)
        }
    }

    /* Muestra una notificación local si el permiso está concedido en el dispositivo*/

    fun mostrarNotificacion(contexto: Context, titulo: String, mensaje: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            val permisoConcedido = ContextCompat.checkSelfPermission(
                contexto,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
            if (!permisoConcedido) return
        }

        val intentAbrirApp = Intent(contexto, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            contexto,
            0,
            intentAbrirApp,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificacion = NotificationCompat.Builder(contexto, CANAL_ID)
            .setSmallIcon(R.drawable.ic_stat_cenamad)
            .setContentTitle(titulo)
            .setContentText(mensaje)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val idUnico = (System.currentTimeMillis() % Int.MAX_VALUE).toInt()
        NotificationManagerCompat.from(contexto).notify(idUnico, notificacion)
    }
}