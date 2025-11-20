package com.raul_fernandez_garcia.worknearby

import com.raul_fernandez_garcia.worknearby.modelo.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {

    @POST("usuarios/crear")
    fun crearUsuario(@Body usuario: Usuario): Call<Usuario>

    @GET("usuarios")
    fun obtenerUsuarios(): Call<List<Usuario>>


}