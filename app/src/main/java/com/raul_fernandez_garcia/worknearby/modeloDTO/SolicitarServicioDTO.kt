package com.raul_fernandez_garcia.WorkNearby_API.modeloDTO

data class SolicitarServicioDTO(
    val emailCliente: String,
    val idTrabajador: Int,
    val idCategoria: Int,
    val descripcion: String,
    val estado: String
)