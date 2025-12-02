package com.raul_fernandez_garcia.worknearby.modeloDTO

data class OfertaDTO(
    val id: Int,
    val titulo: String,
    val descripcion: String?,
    val precio: Double?,
    val nombreCategoria: String,

    val idTrabajador: Int,
    val nombreTrabajador: String,
    val fotoUrlTrabajador: String?,

    val fotoUrlOferta: String?
)