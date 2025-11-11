package com.raul_fernandez_garcia.worknearby.modelo

data class Servicio (
    val id: Int = 0,
    val cliente: Cliente,
    val trabajador: Trabajador,
    val categoria: Categoria,
    val descripcion: String,
    val estado: String,
    val fechaSolicitud: String? = null
)