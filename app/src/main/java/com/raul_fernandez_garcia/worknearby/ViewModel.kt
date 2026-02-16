package com.raul_fernandez_garcia.worknearby

import android.content.Context
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.messaging.FirebaseMessaging
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.CrearOfertaDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.CrearResenaDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.LoginRequest
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.RegistroDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.SolicitarServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.CategoriaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.CrearNotificacionDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.NotificacionDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ResenaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.TrabajadorDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class OfertasViewModel(context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)
    private val _ofertas = MutableStateFlow<List<OfertaDTO>>(emptyList())
    val ofertas: StateFlow<List<OfertaDTO>> = _ofertas

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading
    private val _nombreUsuario = MutableStateFlow("Cargando...")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

    // Con true el boton aparece 1/2 seg si eres trabajador y al comprobar rol desaparece
    // Con false el boton no esta y si eres cliente aparecera despues de comprobar rol
    private val _esTrabajador = MutableStateFlow(false)
    val esTrabajador: StateFlow<Boolean> = _esTrabajador

    init {
        cargarOfertas()
        cargarPerfilUsuario()
    }

    fun cargarOfertas() {
        viewModelScope.launch {
            try {
                val lista = RetrofitClient.api.buscarOfertas(lat = null, lon = null)
                _ofertas.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun cargarPerfilUsuario() {
        viewModelScope.launch {
            try {
                val idUsuarioLogueado = sessionManager.obtenerIdUsuario()
                val rol = sessionManager.obtenerRol()

                _esTrabajador.value = (rol == "trabajador")

                if (idUsuarioLogueado != 0) {
                    val nombreReal = if (rol == "trabajador") {
                        val perfil = RetrofitClient.api.obtenerPerfilTrabajador(idUsuarioLogueado)
                        perfil.usuario.nombre
                    } else {
                        val perfil = RetrofitClient.api.obtenerPerfilCliente(idUsuarioLogueado)
                        perfil.usuario.nombre
                    }

                    _nombreUsuario.value = nombreReal
                } else {
                    _nombreUsuario.value = "Invitado"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _nombreUsuario.value = "Usuario"
            }
        }
    }
}

class OfertasViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(OfertasViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return OfertasViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class ContratosViewModel(context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)
    private val _contratos = MutableStateFlow<List<ServicioDTO>>(emptyList())
    val contratos: StateFlow<List<ServicioDTO>> = _contratos

    private val _nombreUsuario = MutableStateFlow("Cargando...")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Con true el boton aparece 1/2 seg si eres trabajador y al comprobar rol desaparece
    // Con false el boton no esta y si eres cliente aparecera despues de comprobar rol
    private val _esTrabajador = MutableStateFlow(false)
    val esTrabajador: StateFlow<Boolean> = _esTrabajador

    init {
        cargarContratos()
        cargarPerfilUsuario()
    }

    fun cargarContratos() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val idUsuario = sessionManager.obtenerIdUsuario()
                val rol = sessionManager.obtenerRol()
                val soyTrabajador = (rol == "trabajador")

                if (idUsuario != 0) {
                    val lista = RetrofitClient.api.obtenerMisContratos(
                        idUsuario = idUsuario,
                        esTrabajador = soyTrabajador
                    )
                    _contratos.value = lista
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun cargarPerfilUsuario() {
        viewModelScope.launch {
            try {
                val idUsuarioLogueado = sessionManager.obtenerIdUsuario()
                val rol = sessionManager.obtenerRol()

                _esTrabajador.value = (rol == "trabajador")

                if (idUsuarioLogueado != 0) {
                    val nombreReal = if (rol == "trabajador") {
                        val perfil = RetrofitClient.api.obtenerPerfilTrabajador(idUsuarioLogueado)
                        perfil.usuario.nombre
                    } else {
                        val perfil = RetrofitClient.api.obtenerPerfilCliente(idUsuarioLogueado)
                        perfil.usuario.nombre
                    }

                    _nombreUsuario.value = nombreReal
                } else {
                    _nombreUsuario.value = "Invitado"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _nombreUsuario.value = "Usuario"
            }
        }
    }
}

class ContratosViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContratosViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContratosViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class TrabajoViewModel(context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)

    // 1. Datos oferta
    private val _oferta = MutableStateFlow<OfertaDTO?>(null)
    val oferta: StateFlow<OfertaDTO?> = _oferta

    // 2. Datos de Trabajador
    private val _trabajador = MutableStateFlow<TrabajadorDTO?>(null)
    val trabajador: StateFlow<TrabajadorDTO?> = _trabajador

    // 3. Lista de Reseñas
    private val _resenas = MutableStateFlow<List<ResenaDTO>>(emptyList())
    val resenas: StateFlow<List<ResenaDTO>> = _resenas

    // 4. Estado de carga
    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    // 5. Nombre del usuario logueado (Para el menu lateral)
    private val _nombreUsuarioLogueado = MutableStateFlow("Usuario")
    val nombreUsuarioLogueado: StateFlow<String> = _nombreUsuarioLogueado

    // Con true el boton aparece 1/2 seg si eres trabajador y al comprobar rol desaparece
    // Con false el boton no esta y si eres cliente aparecera despues de comprobar rol
    private val _esCliente = MutableStateFlow(false)
    val esCliente: StateFlow<Boolean> = _esCliente

    fun cargarDatos(idOferta: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // A. Descargar Oferta (Para precio y ID trabajador)
                val ofertaDescargada = RetrofitClient.api.obtenerOferta(idOferta)
                _oferta.value = ofertaDescargada

                // B. Cargar perfil del trabajador
                val idDelPintor = ofertaDescargada.idTrabajador
                val perfil = RetrofitClient.api.obtenerTrabajador(idDelPintor)
                _trabajador.value = perfil

                // C. Cargar sus reseñas
                val listaResenas = RetrofitClient.api.obtenerResenas(idDelPintor)
                _resenas.value = listaResenas

                // D. Cargar el nombre del usuario actual (Para el menu)
                val idUsuarioLogueado = sessionManager.obtenerIdUsuario()
                val rol = sessionManager.obtenerRol()

                _esCliente.value = (rol == "cliente")

                // Obtengo el nombre para el menu lateral
                if (idUsuarioLogueado != 0) {
                    val nombreReal = if (rol == "trabajador") {
                        val miPerfil = RetrofitClient.api.obtenerPerfilTrabajador(idUsuarioLogueado)
                        miPerfil.usuario.nombre
                    } else {
                        val miPerfil = RetrofitClient.api.obtenerPerfilCliente(idUsuarioLogueado)
                        miPerfil.usuario.nombre
                    }
                    _nombreUsuarioLogueado.value = nombreReal
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class TrabajoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TrabajoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TrabajoViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class PerfilViewModel(context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)

    // Usamos 'Any?' porque puede ser ClienteDTO o TrabajadorDTO
    private val _perfil = MutableStateFlow<Any?>(null)
    val perfil: StateFlow<Any?> = _perfil

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _esTrabajador = MutableStateFlow(false)
    val esTrabajador: StateFlow<Boolean> = _esTrabajador

    init {
        cargarMiPerfil()
    }

    private fun cargarMiPerfil() {
        viewModelScope.launch {
            _isLoading.value = true
            try {

                val miId = sessionManager.obtenerIdUsuario()
                val rol = sessionManager.obtenerRol()

                val soyTrabajador = (rol == "trabajador")

                _esTrabajador.value = soyTrabajador

                if (soyTrabajador) {
                    val datos = RetrofitClient.api.obtenerPerfilTrabajador(miId)
                    _perfil.value = datos
                } else {
                    val datos = RetrofitClient.api.obtenerPerfilCliente(miId)
                    _perfil.value = datos
                }
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

class PerfilViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PerfilViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PerfilViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class CrearResenaViewModel(context: Context) : ViewModel() {

    val sessionManager = SessionManager(context)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito

    fun publicarResena(idTrabajador: Int, puntuacion: Int, comentario: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val miIdCliente = sessionManager.obtenerIdUsuario()

                if (miIdCliente != 0) {
                    val nuevaResena = CrearResenaDTO(
                        idCliente = miIdCliente,
                        idTrabajador = idTrabajador,
                        puntuacion = puntuacion,
                        comentario = comentario
                    )

                    RetrofitClient.api.publicarResena(nuevaResena)
                    _mensajeExito.value = "Opinion publicada correctamente"
                } else {
                    println("Error: No hay usuario logueado")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetMensaje() {
        _mensajeExito.value = null
    }
}

class CrearResenaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearResenaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CrearResenaViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class LoginViewModel(private val context: Context) : ViewModel() {

    // Estados del formulario
    var email by mutableStateOf("")
    var password by mutableStateOf("")

    // Estados de UI
    var isLoading by mutableStateOf(false)
    var loginError by mutableStateOf<String?>(null)
    var loginExitoso by mutableStateOf(false)

    private val sessionManager = SessionManager(context)

    fun login() {
        if (email.isBlank() || password.isBlank()) {
            loginError = context.getString(R.string.error_campo_vacio)
            return
        }

        viewModelScope.launch {
            isLoading = true
            loginError = null
            try {
                val request = LoginRequest(email, password)

                // Llamada al Backend
                val usuarioDTO = RetrofitClient.api.login(request)

                // Guardamos la sesion en el movil
                sessionManager.guardarSesion(
                    id = usuarioDTO.id,
                    rol = usuarioDTO.rol,
                    nombre = usuarioDTO.nombre
                )

                enviarTokenAlServidor(usuarioDTO.id)

                loginExitoso = true

            } catch (e: Exception) {
                e.printStackTrace()
                loginError = context.getString(R.string.error_dato_incorrecto)
            } finally {
                isLoading = false
            }
        }
    }

    fun enviarTokenAlServidor(idUsuario: Int) {
        // 1. Le pedimos a Google el token unico de este movil
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("FCM", "Error al obtener el token", task.exception)
                return@addOnCompleteListener
            }

            // 2. El token es una cadena larga (ej: "fK3z...")
            val token = task.result

            // 3. Lo enviamos a nuestro servidor (Backend)
            // Usamos un Coroutine para la llamada de red
            viewModelScope.launch {
                try {
                    val response = RetrofitClient.api.actualizarTokenFCM(idUsuario, token)
                    if (response.isSuccessful) {
                        Log.d("FCM", "Token actualizado con exito en el servidor")
                    }
                } catch (e: Exception) {
                    Log.e("FCM", "Error al conectar con el servidor: ${e.message}")
                }
            }
        }
    }
}

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context) as T
    }
}


//------------------------------------------------


class RegistroViewModel(private val context: Context) : ViewModel() {
    // Datos Tabla Usuario (Pantalla 1)
    var nombre by mutableStateOf("")
    var apellidos by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var telefono by mutableStateOf("")

    // Rol (Pantalla 2)
    var rol by mutableStateOf("cliente") // 'cliente' o 'trabajador'

    // Datos Tabla Cliente
    var direccion by mutableStateOf("")
    var ciudad by mutableStateOf("")

    var latitud by mutableStateOf(42.8782) // Coordenada inicial
    var longitud by mutableStateOf(-8.5448)

    // Datos Tabla Trabajador
    var descripcion by mutableStateOf("")
    var radioKm by mutableStateOf("")

    var isLoading by mutableStateOf(false)       // Para mostrar ruedita de carga
    var errorRegistro by mutableStateOf<String?>(null) // Para mostrar mensajes de error
    var registroExitoso by mutableStateOf(false) // Para navegar al login automaticamente

    fun registrarUsuario() {
        if (email.isBlank() || password.isBlank()) {
            errorRegistro = context.getString(R.string.error_campo_vacio)
            return
        }
        viewModelScope.launch {
            isLoading = true
            errorRegistro = null

            try {
                // 2. Preparamos el objeto a enviar (DTO)
                val datos = RegistroDTO(
                    nombre = nombre,
                    apellidos = apellidos,
                    email = email,
                    password = password, // <--- AQUI SE ENVIA SIN ENCRIPTAR
                    telefono = telefono,
                    rol = rol,

                    latitud = latitud,
                    longitud = longitud,

                    // Solo enviamos los datos correspondientes al rol elegido
                    direccion = if (rol == "cliente") direccion else null,
                    ciudad = if (rol == "cliente") ciudad else null,
                    descripcion = if (rol == "trabajador") descripcion else null,
                    radioKm = if (rol == "trabajador") radioKm.toDoubleOrNull() else null
                )

                // 3. Llamada a la API
                // RetrofitClient ya lo tienes configurado en tu proyecto
                RetrofitClient.api.registrarUsuario(datos)

                // Si no salta al 'catch', es que todo fue bien
                registroExitoso = true

            } catch (e: Exception) {
                // Si falla (ej: email repetido, servidor caido)
                e.printStackTrace()
                errorRegistro = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}

class RegistroViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RegistroViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RegistroViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class CrearOfertaViewModel(context: Context) : ViewModel() {

    val sessionManager = SessionManager(context)

    private val contentResolver = context.contentResolver

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito

    private val _categorias = MutableStateFlow<List<CategoriaDTO>>(emptyList())
    val categorias: StateFlow<List<CategoriaDTO>> = _categorias

    init {
        cargarCategorias()
    }

    fun publicarOferta(
        titulo: String,
        descripcion: String,
        precio: Double,
        idCategoria: Int,
        fotoUri: Uri?
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val miIdTrabajador = sessionManager.obtenerIdUsuario()

                if (miIdTrabajador != 0) {
                    val fotoString = fotoUri?.let { uri ->
                        try {
                            val inputStream = contentResolver.openInputStream(uri)
                            val bytes = inputStream?.readBytes()
                            inputStream?.close()
                            if (bytes != null) Base64.encodeToString(
                                bytes,
                                Base64.NO_WRAP
                            ) else null
                        } catch (e: Exception) {
                            e.printStackTrace()
                            null
                        }
                    }

                    val nuevaOferta = CrearOfertaDTO(
                        idTrabajador = miIdTrabajador,
                        idCategoria = idCategoria,
                        titulo = titulo,
                        descripcion = descripcion,
                        precio = precio,
                        fotoUrlOferta = fotoString
                    )

                    RetrofitClient.api.publicarOferta(nuevaOferta)
                    _mensajeExito.value = "Oferta publicada correctamente"

                } else {
                    println("Error: No hay usuario logueado")
                }

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Funcion para descargar la lista del desplegable
    private fun cargarCategorias() {
        viewModelScope.launch {
            try {
                // Llama al endpoint @GET("/api/categorias")
                val lista = RetrofitClient.api.obtenerCategorias()
                _categorias.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resetMensaje() {
        _mensajeExito.value = null
    }
}

class CrearOfertaViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearOfertaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CrearOfertaViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class CrearContratoViewModel(context: Context) : ViewModel() {

    val sessionManager = SessionManager(context)

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    var loginError by mutableStateOf<String?>(null)

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito

    private val _categorias = MutableStateFlow<List<CategoriaDTO>>(emptyList())
    val categorias: StateFlow<List<CategoriaDTO>> = _categorias

    init {
        cargarCategorias()
    }

    fun publicarContrato(
        emailCliente: String,
        idCategoria: Int,
        descripcion: String,
        estado: String
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            loginError = null
            try {
                val miIdTrabajador = sessionManager.obtenerIdUsuario()

                if (miIdTrabajador != 0) {
                    val nuevoServicio = SolicitarServicioDTO(
                        emailCliente = emailCliente,
                        idTrabajador = miIdTrabajador,
                        idCategoria = idCategoria,
                        descripcion = descripcion,
                        estado = estado
                    )

                    RetrofitClient.api.solicitarServicio(nuevoServicio)
                    _mensajeExito.value = "Contrato creado correctamente"

                } else {
                    println("Error: No hay usuario logueado")
                }

            } catch (e: Exception) {
                e.printStackTrace()
                loginError = "Este email no existe"
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Funcion para descargar la lista del desplegable
    private fun cargarCategorias() {
        viewModelScope.launch {
            try {
                // Llama al endpoint @GET("/api/categorias")
                val lista = RetrofitClient.api.obtenerCategorias()
                _categorias.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun resetMensaje() {
        _mensajeExito.value = null
    }
}

class CrearContratoViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CrearContratoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CrearContratoViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}


//------------------------------------------------


class NotificacionesViewModel(context: Context) : ViewModel() {
    val sessionManager = SessionManager(context)
    private val _notificaciones = MutableStateFlow<List<NotificacionDTO>>(emptyList())
    val notificaciones: StateFlow<List<NotificacionDTO>> = _notificaciones

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito

    private val _nombreUsuario = MutableStateFlow("Cargando...")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

    init {
        cargarNotificaciones()
        cargarPerfilUsuario()
    }



    fun cargarNotificaciones() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val idUsuario = sessionManager.obtenerIdUsuario()
                // Asumiendo que tienes este endpoint en tu ApiService
                val lista = RetrofitClient.api.obtenerNotificaciones(idUsuario)
                _notificaciones.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    private fun cargarPerfilUsuario() {
        viewModelScope.launch {
            try {
                val idUsuarioLogueado = sessionManager.obtenerIdUsuario()
                val rol = sessionManager.obtenerRol()

                if (idUsuarioLogueado != 0) {
                    // Accedemos a .usuario.nombre dentro de cada caso para que Kotlin sepa el tipo exacto
                    val nombreReal = if (rol == "trabajador") {
                        val perfil = RetrofitClient.api.obtenerPerfilTrabajador(idUsuarioLogueado)
                        perfil.usuario.nombre
                    } else {
                        val perfil = RetrofitClient.api.obtenerPerfilCliente(idUsuarioLogueado)
                        perfil.usuario.nombre
                    }

                    _nombreUsuario.value = nombreReal
                } else {
                    _nombreUsuario.value = "Invitado"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _nombreUsuario.value = "Usuario"
            }
        }
    }

    // Funcion para enviar la notificacion al servidor
    fun enviarNotificacion(idDestino: Int, titulo: String, mensaje: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val nuevaNotificacion = CrearNotificacionDTO(
                    idUsuario = idDestino,
                    titulo = titulo,
                    mensaje = mensaje
                )
                RetrofitClient.api.enviarNotificacion(nuevaNotificacion)
                _mensajeExito.value = "Notificacion enviada correctamente"
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun resetMensaje() {
        _mensajeExito.value = null
    }
}

// Factory para el ViewModel
class NotificacionesViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NotificacionesViewModel(context) as T
    }
}