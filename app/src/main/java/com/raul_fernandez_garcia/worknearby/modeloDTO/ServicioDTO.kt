package com.raul_fernandez_garcia.worknearby.modeloDTO

data class ServicioDTO(
    val id: Int,
    val descripcion: String?,
    val estado: String,
    val fechaSolicitud: String?,

    val nombreOtroUsuario: String,
    val nombreCategoria: String
)