package com.raul_fernandez_garcia.worknearby

import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.LoginRequest
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.RegistroDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.SolicitarServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ClienteDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ResenaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.TrabajadorDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.UsuarioDTO
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("/api/auth/registro")
    suspend fun registrarUsuario(
        @Body datos: RegistroDTO
    ): UsuarioDTO

    @POST("/api/auth/login")
    suspend fun login(
        @Body loginData: LoginRequest
    ): UsuarioDTO

    @GET("/api/ofertas")
    suspend fun buscarOfertas(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?
    ): List<OfertaDTO>

    @GET("/api/ofertas/{id}")
    suspend fun obtenerOferta(
        @Path("id") id: Int
    ): OfertaDTO

    @GET("/api/trabajadores/{id}")
    suspend fun obtenerTrabajador(
        @Path("id") id: Int
    ): TrabajadorDTO

    @GET("/api/trabajadores/{id}/resenas")
    suspend fun obtenerResenas(
        @Path("id") id: Int
    ): List<ResenaDTO>

    @GET("/api/usuario/cliente/{id}")
    suspend fun obtenerPerfilCliente(
        @Path("id") idUsuario: Int
    ): ClienteDTO

    @PUT("/api/usuario/cliente/{id}")
    suspend fun actualizarPerfilCliente(
        @Path("id") idUsuario: Int,
        @Body datos: ClienteDTO
    ): ClienteDTO

    @POST("/api/servicios/solicitar")
    suspend fun solicitarServicio(
        @Body datos: SolicitarServicioDTO
    ): String

    @GET("/api/servicios/mis-contratos")
    suspend fun obtenerMisContratos(
        @Query("idUsuario") idUsuario: Int,
        @Query("esTrabajador") esTrabajador: Boolean
    ): List<ServicioDTO>
}