package com.raul_fernandez_garcia.worknearby.modeloDTO

data class TrabajadorDTO(
    val id: Int = 0,
    val usuario: UsuarioDTO,
    var descripcion: String,
    var precioHora: Double,
    var radioKm: Double,
    val latitud: Double,
    val longitud: Double
)