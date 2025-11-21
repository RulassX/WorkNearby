package com.raul_fernandez_garcia.worknearby.modelo

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val usuario: UsuarioDTO? = null
)