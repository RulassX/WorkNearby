package com.raul_fernandez_garcia.worknearby.modeloODT

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val usuario: UsuarioDTO? = null
)