package com.raul_fernandez_garcia.worknearby.modelo

data class UsuarioDTO(
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val telefono: String,
    val rol: String,
    val fechaReg: String? = null
)