package com.raul_fernandez_garcia.WorkNearby_API.modeloDTO

data class CrearOfertaDTO(
    val idTrabajador: Int,
    val idCategoria: Int?,
    val titulo: String,
    val descripcion: String?,
    val precio: Double?
)