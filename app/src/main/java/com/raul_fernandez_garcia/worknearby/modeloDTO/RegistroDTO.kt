package com.raul_fernandez_garcia.WorkNearby_API.modeloDTO

data class RegistroDTO(
    val nombre: String,
    val apellidos: String,
    val email: String,
    val password: String,
    val telefono: String,
    val rol: String,

    //val latitud: Double,
    //val longitud: Double,

    val direccion: String? = null,
    val ciudad: String? = null,
    val descripcion: String? = null,
    val precio: Double? = null,
    val radioKm: Double? = null
)