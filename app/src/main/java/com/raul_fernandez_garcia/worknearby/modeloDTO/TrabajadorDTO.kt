package com.raul_fernandez_garcia.worknearby.modeloDTO

data class TrabajadorDTO(
    val id: Int,
    val usuario: UsuarioDTO,
    val descripcion: String?,
    val radioKm: Double,
    val latitud: Double,
    val longitud: Double
)