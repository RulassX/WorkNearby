package com.raul_fernandez_garcia.worknearby

import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.CrearOfertaDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.CrearResenaDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.LoginRequest
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.RegistroDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.SolicitarServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.CategoriaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ClienteDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.CrearNotificacionDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.NotificacionDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ResenaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.TrabajadorDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.UsuarioDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
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

    @GET("/api/categorias")
    suspend fun obtenerCategorias(): List<CategoriaDTO>

    @GET("/api/ofertas")
    suspend fun buscarOfertas(
        @Query("lat") lat: Double?,
        @Query("lon") lon: Double?
    ): List<OfertaDTO>

    @GET("/api/ofertas/{id}")
    suspend fun obtenerOferta(
        @Path("id") id: Int
    ): OfertaDTO

    @POST("/api/ofertas")
    suspend fun publicarOferta(@Body resena: CrearOfertaDTO): OfertaDTO

    @GET("/api/trabajadores/{id}")
    suspend fun obtenerTrabajador(
        @Path("id") id: Int
    ): TrabajadorDTO

    @GET("/api/trabajadores/{id}/resenas")
    suspend fun obtenerResenas(
        @Path("id") id: Int
    ): List<ResenaDTO>

    @POST("/api/resenas")
    suspend fun publicarResena(@Body resena: CrearResenaDTO): ResenaDTO

    @GET("/api/usuario/cliente/{id}")
    suspend fun obtenerPerfilCliente(
        @Path("id") idUsuario: Int
    ): ClienteDTO

    @PUT("/api/usuario/cliente/{id}")
    suspend fun actualizarPerfilCliente(
        @Path("id") idUsuario: Int,
        @Body datos: ClienteDTO
    ): ClienteDTO

    @GET("/api/usuario/trabajador/{id}")
    suspend fun obtenerPerfilTrabajador(@Path("id") idUsuario: Int): TrabajadorDTO

    @POST("/api/servicios")
    suspend fun solicitarServicio(@Body datos: SolicitarServicioDTO): ServicioDTO

    @GET("/api/servicios/mis-contratos")
    suspend fun obtenerMisContratos(
        @Query("idUsuario") idUsuario: Int,
        @Query("esTrabajador") esTrabajador: Boolean
    ): List<ServicioDTO>

    /**
     * Actualiza el token de Firebase del usuario.
     * Se debe llamar cada vez que el usuario hace login o el token cambia.
     */
    @PUT("/api/usuario/{id}/token")
    suspend fun actualizarTokenFCM(
        @Path("id") idUsuario: Int,
        @Query("token") token: String
    ): Response<Unit> // Usamos Response<Unit> porque a veces solo nos importa si fue OK (200)

    /**
     * Obtiene el historial de notificaciones del usuario (el buzon)
     */
    @GET("/api/notificaciones/{idUsuario}")
    suspend fun obtenerNotificaciones(
        @Path("idUsuario") idUsuario: Int
    ): List<NotificacionDTO>

    /**
     * Marca una notificacion como leida
     */
    @PATCH("/api/notificaciones/{id}/leer")
    suspend fun marcarComoLeida(
        @Path("id") idNotificacion: Int
    ): Response<Unit>

    @POST("/api/notificaciones")
    suspend fun enviarNotificacion(@Body dto: CrearNotificacionDTO): Response<Unit>

}