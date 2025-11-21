package com.raul_fernandez_garcia.worknearby

import com.raul_fernandez_garcia.worknearby.modeloODT.ClienteDTO
import com.raul_fernandez_garcia.worknearby.modeloODT.LoginRequest
import com.raul_fernandez_garcia.worknearby.modeloODT.LoginResponse
import com.raul_fernandez_garcia.worknearby.modeloODT.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloODT.TrabajadorDTO
import com.raul_fernandez_garcia.worknearby.modeloODT.UsuarioDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {

    //usuarios

    @POST("usuarios")
    suspend fun crearUsuario(
        @Body usuario: UsuarioDTO
    ): UsuarioDTO

    @GET("usuarios/{id}")
    suspend fun obtenerUsuario(
        @Path("id") id: Int
    ): UsuarioDTO

    @GET("usuarios/email/{email}")
    suspend fun obtenerUsuarioPorEmail(
        @Path("email") email: String
    ): UsuarioDTO

    @POST("login")
    suspend fun login(
        @Body loginData: LoginRequest
    ): LoginResponse


    //trabajadores

    @GET("trabajadores")
    suspend fun listarTrabajadores(): List<TrabajadorDTO>

    @GET("trabajadores/{id}")
    suspend fun obtenerTrabajador(
        @Path("id") id: Int
    ): TrabajadorDTO


    //clientes

    @POST("clientes")
    suspend fun crearCliente(
        @Body cliente: ClienteDTO
    ): ClienteDTO

    @GET("clientes/{id}")
    suspend fun obtenerCliente(
        @Path("id") id: Int
    ): ClienteDTO


    //servicios

    @POST("servicios")
    suspend fun crearServicio(
        @Body servicio: ServicioDTO
    ): ServicioDTO

    @GET("servicios/cliente/{idCliente}")
    suspend fun serviciosPorCliente(
        @Path("idCliente") idCliente: Int
    ): List<ServicioDTO>

    @GET("servicios/trabajador/{idTrabajador}")
    suspend fun serviciosPorTrabajador(
        @Path("idTrabajador") idTrabajador: Int
    ): List<ServicioDTO>
}