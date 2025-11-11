package com.raul_fernandez_garcia.worknearby.modelo

data class Usuario(
    val id: Int = 0,
    val nombre: String,
    val apellidos: String,
    val email: String,
    val telefono: String,
    val rol: String
)