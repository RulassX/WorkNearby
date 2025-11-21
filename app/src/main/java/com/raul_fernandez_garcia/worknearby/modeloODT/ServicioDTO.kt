package com.raul_fernandez_garcia.worknearby.modeloODT

data class ServicioDTO (
    val id: Int = 0,
    val cliente: ClienteDTO,
    val trabajador: TrabajadorDTO,
    val categoria: CategoriaDTO,
    val descripcion: String,
    val estado: String,
    val fechaSolicitud: String? = null
)