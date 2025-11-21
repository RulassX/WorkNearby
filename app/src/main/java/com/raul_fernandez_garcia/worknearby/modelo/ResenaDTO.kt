package com.raul_fernandez_garcia.worknearby.modelo

data class ResenaDTO (
    val id: Int = 0,
    val cliente: ClienteDTO,
    val trabajador: TrabajadorDTO,
    val puntuacion: Int,
    val comentario: String,
    val fecha: String? = null
)