package com.raul_fernandez_garcia.WorkNearby_API.modeloDTO

data class SolicitarServicioDTO(
    val idCliente: Int,
    val idTrabajador: Int,
    val idCategoria: Int?,
    val descripcion: String
)