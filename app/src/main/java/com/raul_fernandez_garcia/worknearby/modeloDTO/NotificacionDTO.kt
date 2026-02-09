package com.raul_fernandez_garcia.worknearby.modeloDTO

data class NotificacionDTO(
    val idNotificacion: Int,
    val idUsuario: Int,
    val titulo: String,
    val mensaje: String,
    val leido: Boolean = false,
    val fechaEnvio: String? = null
)