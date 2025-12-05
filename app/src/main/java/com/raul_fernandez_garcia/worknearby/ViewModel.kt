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

class OfertasViewModel : ViewModel() {
    private val _ofertas = MutableStateFlow<List<OfertaDTO>>(emptyList())
    val ofertas: StateFlow<List<OfertaDTO>> = _ofertas

    private val _nombreUsuario = MutableStateFlow("Cargando...")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

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
                // ID hardcodeado 1 (Juan) para pruebas. Luego vendra del Login.
                val idUsuarioLogueado = 1
                val perfil = RetrofitClient.api.obtenerPerfilCliente(idUsuarioLogueado)

                // Actualizamos la variable con el nombre real
                _nombreUsuario.value = perfil.usuario.nombre
            } catch (e: Exception) {
                e.printStackTrace()
                _nombreUsuario.value = "Usuario"
            }
        }
    }
}

//------------------------------------------------

class ContratosViewModel : ViewModel() {
    private val _contratos = MutableStateFlow<List<ServicioDTO>>(emptyList())
    val contratos: StateFlow<List<ServicioDTO>> = _contratos

    private val _nombreUsuario = MutableStateFlow("Cargando...")
    val nombreUsuario: StateFlow<String> = _nombreUsuario

    init {
        cargarContratos()
        cargarPerfilUsuario()
    }

    fun cargarContratos() {
        viewModelScope.launch {
            try {
                //AQUi SE DEBE USAR EL ID DEL USUARIO LOGUEADO (Desde SharedPreferences)
                // Por ahora pongo '1' y 'false' (cliente) para probar que funciona
                val myId = 1
                val soyTrabajador = false

                val lista = RetrofitClient.api.obtenerMisContratos(
                    idUsuario = myId,
                    esTrabajador = soyTrabajador
                )
                _contratos.value = lista
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun cargarPerfilUsuario() {
        viewModelScope.launch {
            try {
                //Aqui usamos el ID 1 hardcodeado por ahora (como eres Cliente)
                //Mas adelante esto vendra de SharedPreferences
                val idUsuarioLogueado = 1

                //Llamamos al endpoint que creamos en UsuarioController: /api/user/cliente/{id}
                val perfil = RetrofitClient.api.obtenerPerfilCliente(idUsuarioLogueado)

                //Actualizamos el estado con el nombre real
                _nombreUsuario.value = perfil.usuario.nombre

            } catch (e: Exception) {
                _nombreUsuario.value = "Usuario" // Fallback si hay error
                e.printStackTrace()
            }
        }
    }
}

//------------------------------------------------

class TrabajoViewModel : ViewModel() {
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

    private val _esCliente = MutableStateFlow(true) // Lo pongo true para probar
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
                val miPerfil = RetrofitClient.api.obtenerPerfilCliente(1)
                _nombreUsuarioLogueado.value = miPerfil.usuario.nombre

                // Si mi ID es 1 (Juan), soy cliente.
                // Mas adelante esto vendra de SharedPreferences
                val miIdLogueado = 1
                _esCliente.value = (miIdLogueado == 1)

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
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

class CrearResenaViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _mensajeExito = MutableStateFlow<String?>(null)
    val mensajeExito: StateFlow<String?> = _mensajeExito

    fun enviarResena(idTrabajador: Int, puntuacion: Int, comentario: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                //ID Cliente (1 = Juan).
                // Cuando tengas Login real, esto vendra de las preferencias.
                val miIdCliente = 1

                val nuevaResena = CrearResenaDTO(
                    idCliente = miIdCliente,
                    idTrabajador = idTrabajador,
                    puntuacion = puntuacion,
                    comentario = comentario
                )

                RetrofitClient.api.publicarResena(nuevaResena)
                _mensajeExito.value = "¡Opinión publicada correctamente!"
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