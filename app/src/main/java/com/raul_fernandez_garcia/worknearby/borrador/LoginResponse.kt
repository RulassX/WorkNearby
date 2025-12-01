package com.raul_fernandez_garcia.worknearby.borrador

import com.raul_fernandez_garcia.worknearby.modeloDTO.UsuarioDTO

data class LoginResponse(
    val success: Boolean,
    val message: String,
    val usuario: UsuarioDTO? = null
)