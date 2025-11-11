package com.raul_fernandez_garcia.worknearby.modelo

data class Resena (
    val id: Int = 0,
    val cliente: Cliente,
    val trabajador: Trabajador,
    val puntuacion: Int,
    val comentario: String,
    val fecha: String? = null
)