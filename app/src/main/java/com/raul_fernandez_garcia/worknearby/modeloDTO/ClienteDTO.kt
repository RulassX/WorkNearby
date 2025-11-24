package com.raul_fernandez_garcia.worknearby.modeloDTO

data class ClienteDTO (
    val id: Int = 0,
    val usuario: UsuarioDTO,
    val direccion: String,
    val ciudad: String,
    val latitud: Double,
    val longitud: Double
)


