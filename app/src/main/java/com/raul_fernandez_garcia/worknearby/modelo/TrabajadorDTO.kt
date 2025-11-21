package com.raul_fernandez_garcia.worknearby.modelo

data class TrabajadorDTO(
    val id: Int = 0,
    val usuarioDTO: UsuarioDTO,
    val descripcion: String,
    val precioHora: Double,
    val radioKm: Double,
    val latitud: Double,
    val longitud: Double
)