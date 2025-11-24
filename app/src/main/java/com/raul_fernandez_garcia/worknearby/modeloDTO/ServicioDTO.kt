package com.raul_fernandez_garcia.worknearby.modeloDTO

data class ServicioDTO (
    val id: Int = 0,
    val cliente: ClienteDTO,
    val trabajador: TrabajadorDTO,
    val categoria: CategoriaDTO,
    var descripcion: String,
    var estado: String,
    val fechaSolicitud: String? = null
)