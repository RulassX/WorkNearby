package com.raul_fernandez_garcia.worknearby

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.CrearResenaDTO
import com.raul_fernandez_garcia.WorkNearby_API.modeloDTO.LoginRequest
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ResenaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.TrabajadorDTO
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

    fun enviarResena(idTrabajador: Int, puntuacion: Int, comentario: String) {
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
                    _mensajeExito.value = "Opinión publicada correctamente"
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
            loginError = "Rellena todos los campos"
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

                loginExitoso = true

            } catch (e: Exception) {
                e.printStackTrace()
                loginError = "Email o contraseña incorrectos"
            } finally {
                isLoading = false
            }
        }
    }
}

class LoginViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return LoginViewModel(context) as T
    }
}