package com.raul_fernandez_garcia.worknearby.modeloDTO

data class UsuarioDTO(
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val telefono: String,
    var rol: String,
    val fechaReg: String? = null
)