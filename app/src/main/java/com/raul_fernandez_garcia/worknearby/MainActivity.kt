package com.raul_fernandez_garcia.worknearby

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ResenaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.TrabajadorDTO
import com.raul_fernandez_garcia.worknearby.ui.theme.WorkNearbyTheme
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            appWorkNearby(navController)

        }
    }
}

@Composable
private fun appWorkNearby(navController: NavHostController) {
    WorkNearbyTheme {
        Surface() {
            appNavigation(navController)
        }
    }
}

@Composable
fun appNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "ofertas"
    ) {

        composable("ofertas") {
            BuscarOfertas(navController)
        }

        composable("contratos") {
            BuscarContratos(navController)
        }

        composable(
            route = "trabajo_ofertado/{id}",
            arguments = listOf(navArgument("id") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("id") ?: 0

            TrabajoOfertado(navController, idOferta = id)
        }

        composable("perfil") {
            Perfil(navController)
        }

        composable("chats") {
            BuscarChats(navController)
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuscarOfertas(
    navController: NavHostController, viewModel: OfertasViewModel = viewModel()
) {
    val listaOfertasReal by viewModel.ofertas.collectAsState()

    val nombre by viewModel.nombreUsuario.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Hola $nombre",
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(text = "Mi Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("perfil")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text(text = "Mis Contratos") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("contratos")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
                Text(text = "OP3", modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "WorkNearby"
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (listaOfertasReal.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Cargando ofertas o no hay disponibles...")
                }
            } else {
                ListaOfertas(
                    ofertas = listaOfertasReal,
                    modifier = Modifier.padding(paddingValues),
                    onOfertaClick = { id ->
                        navController.navigate("trabajo_ofertado/$id")
                    }
                )
            }
        }
    }
}

@Composable
fun ListaOfertas(
    ofertas: List<OfertaDTO>,
    onOfertaClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {

    LazyColumn(
        modifier
            .background(Color.Gray)
            .fillMaxSize()
            .padding(PaddingValues())
            .padding(top = 10.dp)
    ) {
        items(ofertas) { oferta ->

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    //.height(150.dp)
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxSize()
                    .clickable { onOfertaClick(oferta.idTrabajador) }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        Text(
                            text = oferta.nombreCategoria,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 15.dp)
                        )
                        Text(
                            text = oferta.nombreTrabajador,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(bottom = 15.dp, start = 15.dp),
                            //textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Precio: ${oferta.precio} €/h",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 15.dp),
                        )
                    }

                    AsyncImage(
                        model = oferta.fotoUrlOferta ?: R.drawable.imagenvacia,
                        contentDescription = "Foto de oferta",
                        modifier = Modifier
                            .padding(15.dp)
                            .width(115.dp)
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuscarContratos(
    navController: NavHostController,
    viewModel: ContratosViewModel = viewModel()
) {
    val listaContratosReal by viewModel.contratos.collectAsState()
    val nombre by viewModel.nombreUsuario.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Hola $nombre",
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(text = "Mi Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("perfil")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text(text = "Ofertas de trabajo") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("ofertas")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Contratos"
                        )

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (listaContratosReal.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text("No tienes contratos activos.")
                }
            } else {
                ListaContratos(
                    contratos = listaContratosReal,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
private fun ListaContratos(contratos: List<ServicioDTO>, modifier: Modifier = Modifier) {
    //val nombres = listOf()
    //val apellidos = listOf()

    LazyColumn(
        modifier
            .background(Color.Gray)
            .fillMaxSize()
            .padding(PaddingValues())
            .padding(top = 10.dp)
    ) {
        items(contratos) { contrato ->

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    //.height(150.dp)
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxHeight(),
                    ) {
                        Text(
                            text = "Servicio: ${contrato.nombreCategoria}",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 15.dp),
                            //textAlign = TextAlign.Center,
                        )

                        Text(
                            text = contrato.nombreOtroUsuario,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 15.dp),
                        )

                        contrato.descripcion?.let {
                            Text(
                                text = it,
                                fontSize = 14.sp,
                                maxLines = 2,
                                color = Color.Gray,
                                modifier = Modifier
                                    .padding(start = 15.dp, bottom = 15.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        val colorEstado = when (contrato.estado.lowercase()) {
                            "pendiente" -> Color(0xFFFFA000) //Naranja
                            "aceptado" -> Color(0xFF388E3C)  //Verde
                            "rechazado" -> Color.Red                //Rojo
                            else -> Color.Gray
                        }

                        Text(
                            text = contrato.estado.uppercase(),
                            fontSize = 17.sp,
                            color = colorEstado,
                            modifier = Modifier
                                .padding(start = 15.dp)
                        )

                        contrato.fechaSolicitud?.let {
                            Text(
                                text = "Fecha: $it", fontSize = 17.sp,
                                modifier = Modifier
                                    .padding(start = 15.dp, bottom = 15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrabajoOfertado(
    navController: NavHostController,
    idOferta: Int,
    viewModel: TrabajoViewModel = viewModel()
) {

    LaunchedEffect(idOferta) {
        viewModel.cargarDatos(idOferta)
    }

    val oferta by viewModel.oferta.collectAsState()
    val trabajador by viewModel.trabajador.collectAsState()
    val resenas by viewModel.resenas.collectAsState()
    val nombreUsuarioMenu by viewModel.nombreUsuarioLogueado.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Hola $nombreUsuarioMenu",
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(text = "Mi Perfil") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("perfil")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text(text = "Ofertas de trabajo") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("ofertas")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text(text = "Mis Contratos") },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("contratos")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Detalles"
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (isLoading || trabajador == null) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                val user = trabajador!!.usuario

                LazyColumn(
                    modifier = Modifier
                        .background(Color.Gray)
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        Row(modifier = Modifier.padding(16.dp)) {
                            Column(modifier = Modifier.weight(1f)) {
                                // Nombre Real
                                Text(
                                    text = user.nombre,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 4.dp)
                                )
                                // Apellidos Reales
                                Text(
                                    text = user.apellidos,
                                    fontSize = 20.sp,
                                    modifier = Modifier.padding(bottom = 8.dp)
                                )
                                // Profesión (Descripción corta)
                                Text(
                                    text = trabajador!!.descripcion ?: "Profesional",
                                    fontSize = 18.sp,
                                    color = Color.Gray,
                                    modifier = Modifier.padding(bottom = 15.dp)
                                )
                                // Contacto Real
                                Text(text = "Telf.: ${user.telefono}", fontSize = 16.sp)
                                Text(text = "Email: ${user.email}", fontSize = 16.sp)

                                // Precio Real
                                Text(
                                    text = "${oferta!!.precio} €/hora",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(top = 8.dp)
                                )
                            }

                            // FOTO REAL (Coil)
                            AsyncImage(
                                model = user.fotoUrl ?: R.drawable.fotoperfilvacia,
                                contentDescription = "Foto perfil",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop,
                                alignment = Alignment.TopEnd
                            )
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                        Text(
                            text = "Opiniones de clientes",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 16.dp, bottom = 8.dp)
                        )
                    }

                    //LISTA DE RESEÑAS
                    if (resenas.isEmpty()) {
                        item {
                            Text(
                                "No hay reseñas todavía.",
                                modifier = Modifier.padding(16.dp),
                            )
                        }
                    } else {
                        items(resenas) { resena ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp, vertical = 4.dp),
                                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
                            ) {
                                Column(modifier = Modifier.padding(12.dp)) {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = resena.nombreCliente,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Text(text = "${resena.puntuacion}/5")
                                    }
                                    if (!resena.comentario.isNullOrEmpty()) {
                                        Text(
                                            text = resena.comentario,
                                            modifier = Modifier.padding(top = 4.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

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

            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                _isLoading.value = false
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Perfil(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "OP1", modifier = Modifier.padding(16.dp))
                Text(text = "OP2", modifier = Modifier.padding(16.dp))
                Text(text = "OP3", modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Perfil"
                        )

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center
                        ) {
                            Image(
                                alignment = Alignment.Center,
                                modifier = Modifier
                                    .padding(50.dp)
                                    .size(140.dp)
                                    .clip(CircleShape)
                                    .fillMaxWidth(),
                                painter = painterResource(id = R.drawable.ic_launcher_background),
                                contentDescription = "imagen",
                                contentScale = ContentScale.Crop
                            )

                            Text(
                                text = "Raúl",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(15.dp)
                            )
                            Text(
                                text = "Fernández García",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(bottom = 15.dp, start = 15.dp)
                            )
                            Text(
                                text = "Telf.: 123456789",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(bottom = 15.dp, start = 15.dp)
                            )
                            Text(
                                text = "Email: ejemplo@gmail.com",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(bottom = 15.dp, start = 15.dp)
                            )
                            Text(
                                text = "Pintor",
                                fontSize = 22.sp,
                                modifier = Modifier.padding(bottom = 15.dp, start = 15.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuscarChats(navController: NavHostController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "OP1", modifier = Modifier.padding(16.dp))
                Text(text = "OP2", modifier = Modifier.padding(16.dp))
                Text(text = "OP3", modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Chats"
                        )

                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch {
                                if (drawerState.isClosed) {
                                    drawerState.open()
                                } else {
                                    drawerState.close()
                                }
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Default.Menu,
                                contentDescription = "Abrir menu"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            ListaChats(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
private fun ListaChats(modifier: Modifier = Modifier) {
    val nombres = listOf("Raúl", "Brais", "Laura", "Carlos", "Lucia", "Pedro", "Maria")
    val apellidos =
        listOf(
            "Fernández García",
            "Fernández",
            "Gomez",
            "Varela",
            "Rodriguez",
            "Lopez",
            "García"
        )

    LazyColumn(
        modifier
            .background(Color.Gray)
            .fillMaxSize()
            .padding(PaddingValues())
            .padding(top = 10.dp)
    ) {
        items(nombres.zip(apellidos)) { (nombre, apellido) ->

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Image(
                        modifier = Modifier
                            .padding(15.dp)
                            .size(70.dp)
                            .clip(CircleShape),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "imagen",
                        contentScale = ContentScale.Crop
                    )

                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = nombre + " " + apellido,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxSize(),
                        )
                    }
                }
            }
        }
    }
}