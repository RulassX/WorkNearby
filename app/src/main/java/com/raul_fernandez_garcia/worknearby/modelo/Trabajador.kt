package com.raul_fernandez_garcia.worknearby.modelo

data class Trabajador(
    val id: Int = 0,
    val usuario: Usuario,
    val descripcion: String,
    val precioHora: Double,
    val radioKm: Double,
    val latitud: Double,
    val longitud: Double
)