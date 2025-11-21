package com.raul_fernandez_garcia.worknearby.modelo

data class OfertaDTO(
    val id: Int,
    val trabajador: TrabajadorDTO,
    val titulo: String,
    val descripcion: String,
    val precio: Double?,
    val categoria: CategoriaDTO?,
    val foto: String?,
    val fechaPublicacion: String
)