package com.raul_fernandez_garcia.worknearby.modeloDTO

data class UsuarioDTO(
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    var fcmToken: String? = null,
    val email: String,
    val password: String? = null,
    val telefono: String,
    val rol: String,
    val fechaReg: String? = null,

    val fotoUrl: String? = null
)
