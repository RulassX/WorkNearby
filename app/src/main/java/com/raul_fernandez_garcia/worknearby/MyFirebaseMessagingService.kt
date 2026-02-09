package com.raul_fernandez_garcia.worknearby

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.util.Log

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Se ejecuta cuando llega una notificación y la app está en primer plano
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d("NOTIF", "Mensaje recibido de: ${remoteMessage.from}")

        // Aquí podrías mostrar un Toast o una notificación personalizada
        remoteMessage.notification?.let {
            Log.d("NOTIF", "Cuerpo: ${it.body}")
        }
    }

    // Se ejecuta si Google decide cambiar el token (poco frecuente)
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NOTIF", "Nuevo token generado: $token")
        // Aquí deberías llamar a tu API para actualizar el token en la DB
    }
}