package com.raul_fernandez_garcia.worknearby.modeloDTO

data class CrearNotificacionDTO(
    val idEmisor: Int,
    val idReceptor: Int,
    val titulo: String,
    val mensaje: String
)