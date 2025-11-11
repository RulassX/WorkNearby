package com.raul_fernandez_garcia.worknearby.modelo

data class Cliente (
    val id: Int = 0,
    val usuario: Usuario,
    val direccion: String,
    val ciudad: String
)


