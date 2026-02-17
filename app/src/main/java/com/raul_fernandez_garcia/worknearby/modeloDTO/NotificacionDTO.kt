package com.raul_fernandez_garcia.worknearby.modeloDTO

data class NotificacionDTO(
    val idNotificacion: Int,
    val idEmisor: Int,
    val idReceptor: Int,
    val titulo: String,
    val mensaje: String,
    val leido: Boolean = false,
    val fechaEnvio: String? = null
)
