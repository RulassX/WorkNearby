package com.raul_fernandez_garcia.WorkNearby_API.modeloDTO

data class CrearResenaDTO(
    val idCliente: Int,
    val idTrabajador: Int,
    val puntuacion: Int, // 1 a 5
    val comentario: String
)