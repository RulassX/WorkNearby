package com.raul_fernandez_garcia.worknearby.modeloODT

data class ClienteDTO (
    val id: Int = 0,
    val usuarioDTO: UsuarioDTO,
    val direccion: String,
    val ciudad: String
)


