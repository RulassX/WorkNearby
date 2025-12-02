package com.raul_fernandez_garcia.worknearby.modeloDTO

data class ResenaDTO(
    val id: Int,
    val puntuacion: Int,
    val comentario: String?,
    val fecha: String?,

    val nombreCliente: String
)