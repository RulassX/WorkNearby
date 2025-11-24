package com.raul_fernandez_garcia.worknearby.modeloDTO

data class OfertaDTO(
    val id: Int,
    val trabajador: TrabajadorDTO,
    var titulo: String,
    var descripcion: String,
    var precio: Double?,
    val categoria: CategoriaDTO?,
    val foto: String?,
    val fechaPublicacion: String
)