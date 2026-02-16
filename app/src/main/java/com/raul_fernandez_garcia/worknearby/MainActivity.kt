package com.raul_fernandez_garcia.worknearby

import android.annotation.SuppressLint
import android.content.Context
import android.location.Geocoder
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AssistChip
import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.AsyncImage
import com.example.compose.AppTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import com.raul_fernandez_garcia.worknearby.modeloDTO.CategoriaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ClienteDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.NotificacionDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.ServicioDTO
import com.raul_fernandez_garcia.worknearby.modeloDTO.TrabajadorDTO
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.tertiaryContainer
                ) {
                    val navController = rememberNavController()
                    appWorkNearby(navController)
                }
            }

        }
    }
}

@Composable
private fun appWorkNearby(navController: NavHostController) {
    AppTheme {
        Surface() {
            appNavigation(navController)
        }
    }
}

@Composable
fun appNavigation(navController: NavHostController) {

    val context = LocalContext.current
    val registroViewModel: RegistroViewModel =
        viewModel(factory = RegistroViewModelFactory(context))

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {

        composable("login") {
            VentanaLogin(navController)
        }

        composable("registro_usuario") {
            VentanaRegistroUsuario(navController, registroViewModel)
        }

        composable("escoger_rol") {
            VentanaSeleccionRol(navController, registroViewModel)
        }

        composable("registro_cliente") {
            VentanaRegistroCliente(navController, registroViewModel)
        }

        composable("registro_trabajador") {
            VentanaRegistroTrabajador(navController, registroViewModel)
        }

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

        composable(
            route = "crear_resena/{idTrabajador}",
            arguments = listOf(navArgument("idTrabajador") { type = NavType.IntType })
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getInt("idTrabajador") ?: 0

            // Llamada correcta
            EscribirResena(navController, idTrabajador = id)
        }

        composable("crear_oferta") {
            EscribirOferta(navController)
        }

        composable("crear_contrato") {
            EscribirContrato(navController)
        }

        composable("historial_notificacion") {
            HistorialNotificaciones(navController)
        }
    }
}


//------------------------------------------------


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuscarOfertas(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val viewModel: OfertasViewModel = viewModel(
        factory = OfertasViewModelFactory(context)
    )

    LaunchedEffect(Unit) {
        viewModel.cargarOfertas()
    }

    val listaOfertasReal by viewModel.ofertas.collectAsState()

    val nombre by viewModel.nombreUsuario.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val esTrabajador by viewModel.esTrabajador.collectAsState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.menu_hola, nombre),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_perfil)) },
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
                    label = { Text(stringResource(R.string.menu_contratos)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("contratos")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_notificaciones)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("historial_notificacion")
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        // Color de fondo de la barra
                        containerColor = MaterialTheme.colorScheme.primary,

                        // Color del texto del titulo
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,

                        // Color de los iconos (menu, flecha atras)
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),

                    title = {
                        Text(
                            text = stringResource(R.string.app_name)
                        )
                    },
                    navigationIcon = {
                        FilledIconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),

                            onClick = {
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
                                contentDescription = stringResource(R.string.cd_abrir_menu)
                            )
                        }
                    },
                    actions = {

                    }


                )


            },
            // Boton solo visible para trabajador

            floatingActionButton = {
                if (esTrabajador) {

                    FloatingActionButton(
                        onClick = { navController.navigate("crear_oferta") },
                        modifier = Modifier
                            .size(70.dp)
                    ) {
                        Icon(Icons.Filled.Add, stringResource(R.string.cd_anadir_oferta))
                    }
                }
            }


        ) { paddingValues ->
            if (listaOfertasReal.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.msg_cargando_ofertas))
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
            .fillMaxSize()
            .padding(PaddingValues())
            .padding(top = 10.dp)
    ) {
        items(ofertas) { oferta ->

            val precio = oferta.precio

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier
                    //.height(150.dp)
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxSize()
                    .clickable { onOfertaClick(oferta.id) }
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

                            text = stringResource(R.string.label_precio_formato, precio ?: 0.0),
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 15.dp),
                        )
                    }

                    AsyncImage(
                        model = oferta.fotoUrlOferta ?: R.drawable.imagenvacia,
                        contentDescription = stringResource(R.string.cd_foto_oferta),
                        modifier = Modifier
                            .padding(15.dp)
                            .size(115.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}


//------------------------------------------------


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuscarContratos(
    navController: NavHostController,
) {
    val context = LocalContext.current
    val viewModel: ContratosViewModel = viewModel(
        factory = ContratosViewModelFactory(context)
    )

    val listaContratosReal by viewModel.contratos.collectAsState()
    val nombre by viewModel.nombreUsuario.collectAsState()

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val esTrabajador by viewModel.esTrabajador.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarContratos()
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {

                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.menu_hola, nombre),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_perfil)) },
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
                    label = { Text(stringResource(R.string.menu_ofertas)) },
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
                    label = { Text(stringResource(R.string.menu_notificaciones)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("historial_notificacion")
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        // Color de fondo de la barra
                        containerColor = MaterialTheme.colorScheme.primary,

                        // Color del texto del titulo
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,

                        // Color de los iconos (menu, flecha atras)
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),

                    title = {
                        Text(
                            text = stringResource(R.string.titulo_mis_contratos)
                        )

                    },
                    navigationIcon = {
                        FilledIconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            onClick = {
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
                                contentDescription = stringResource(R.string.cd_abrir_menu)
                            )
                        }
                    },
                    actions = {

                    }
                )
            },
            // Boton solo visible para trabajador

            floatingActionButton = {
                if (esTrabajador) {

                    FloatingActionButton(
                        onClick = { navController.navigate("crear_contrato") },
                        modifier = Modifier
                            .size(70.dp)
                    ) {
                        Icon(Icons.Filled.Add, stringResource(R.string.cd_anadir_oferta))
                    }
                }
            }
        ) { paddingValues ->
            if (listaContratosReal.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    Text(stringResource(R.string.msg_cargando_contratos))
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
            .fillMaxSize()
            .padding(PaddingValues())
            .padding(top = 10.dp)
    ) {
        items(contratos) { contrato ->

            val nameCatg = contrato.nombreCategoria

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
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
                            text = stringResource(R.string.label_servicio_categoria, nameCatg),
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
                                    .padding(start = 15.dp, bottom = 15.dp, end = 15.dp)
                            )
                        }

                        Spacer(modifier = Modifier.weight(1f))

                        val colorEstado = when (contrato.estado.lowercase()) {
                            "pendiente" -> Color(0xFFFFA000)    // Naranja
                            "aceptado" -> Color(0xFF388E3C)     // Verde
                            "rechazado" -> Color.Red                    // Rojo
                            else -> Color.Gray
                        }

                        val textoEstado = when (contrato.estado.lowercase()) {
                            "pendiente" -> stringResource(R.string.estado_pendiente)
                            "aceptado" -> stringResource(R.string.estado_aceptado)
                            "rechazado" -> stringResource(R.string.estado_rechazado)
                            else -> contrato.estado
                        }

                        Text(
                            text = textoEstado.uppercase(),
                            fontSize = 17.sp,
                            color = colorEstado,
                            modifier = Modifier.padding(start = 15.dp)
                        )

                        contrato.fechaSolicitud?.let {
                            val date = it
                            Text(
                                text = stringResource(R.string.label_fecha, date),
                                fontSize = 17.sp,
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


//------------------------------------------------


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrabajoOfertado(
    navController: NavHostController,
    idOferta: Int,
) {
    val context = LocalContext.current
    val viewModel: TrabajoViewModel = viewModel(
        factory = TrabajoViewModelFactory(context)
    )

    val oferta by viewModel.oferta.collectAsState()
    val trabajador by viewModel.trabajador.collectAsState()
    val resenas by viewModel.resenas.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val esCliente by viewModel.esCliente.collectAsState()

    LaunchedEffect(idOferta) {
        viewModel.cargarDatos(idOferta)
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    // Color de fondo de la barra
                    containerColor = MaterialTheme.colorScheme.primary,

                    // Color del texto del titulo (debe contrastar con el fondo)
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,

                    // Color de los iconos (menu, flecha atras)
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),

                title = { Text(stringResource(R.string.titulo_detalles)) },
                navigationIcon = {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_cancelar)
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
            val telf = user.telefono
            val email = user.email
            val precio = oferta!!.precio

            LazyColumn(
                modifier = Modifier
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
                            // Profesion (Descripcion corta)
                            Text(
                                text = oferta!!.descripcion
                                    ?: stringResource(R.string.sin_descripcion),
                                fontSize = 18.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(bottom = 15.dp)
                            )
                            // Contacto Real
                            Text(stringResource(R.string.label_telf, telf), fontSize = 16.sp)
                            Text(stringResource(R.string.label_email_info, email), fontSize = 16.sp)

                            // Precio Real
                            Text(
                                text = stringResource(R.string.precio_hora_simple, precio ?: 0.0),
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        Column(
                            modifier = Modifier
                                .fillMaxHeight(),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            // FOTO REAL (Coil)
                            AsyncImage(
                                model = user.fotoUrl ?: R.drawable.fotoperfilvacia,
                                contentDescription = stringResource(R.string.cd_foto_perfil),
                                modifier = Modifier
                                    .padding(5.dp)
                                    .padding(bottom = 30.dp)
                                    .size(120.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                                contentScale = ContentScale.Crop,
                            )


                            //Boton para enviar notificacion
                            FloatingActionButton(
                                onClick = { navController.navigate("") },
                                modifier = Modifier
                                    .size(70.dp)
                            ) {
                                Icon(
                                    Icons.AutoMirrored.Filled.Send,
                                    stringResource(R.string.cd_notificar)
                                )
                            }


                        }
                    }

                    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = stringResource(R.string.titulo_opiniones),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )

                        // Boton solo visible para cliente
                        if (esCliente) {

                            //Boton para reseñas
                            FilledIconButton(
                                onClick = {
                                    if (trabajador != null) {
                                        navController.navigate("crear_resena/${trabajador!!.id}")
                                    }
                                },
                                modifier = Modifier.size(35.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Add,
                                    contentDescription = stringResource(R.string.cd_anadir_resena)
                                )
                            }
                        }
                    }
                }

                //LISTA DE RESEÑAS
                if (resenas.isEmpty()) {
                    item {
                        Text(
                            stringResource(R.string.msg_no_hay_resenas),
                            modifier = Modifier.padding(16.dp),
                        )
                    }
                } else {
                    items(resenas) { resena ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 4.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
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


//------------------------------------------------


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Perfil(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: PerfilViewModel = viewModel(
        factory = PerfilViewModelFactory(context)
    )

    val perfil by viewModel.perfil.collectAsState()
    val esTrabajador by viewModel.esTrabajador.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val nombreUsuarioMenu = if (perfil != null) {
        if (esTrabajador) (perfil as TrabajadorDTO).usuario.nombre
        else (perfil as ClienteDTO).usuario.nombre
    } else {
        "Usuario"
    }

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.menu_hola, nombreUsuarioMenu),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(text = stringResource(R.string.menu_ofertas)) },
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
                    label = { Text(stringResource(R.string.menu_contratos)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("contratos")
                        }
                    },
                    modifier = Modifier.padding(horizontal = 12.dp)
                )

                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_notificaciones)) },
                    selected = false,
                    onClick = {
                        scope.launch {
                            drawerState.close()
                            navController.navigate("historial_notificacion")
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        // Color de fondo de la barra
                        containerColor = MaterialTheme.colorScheme.primary,

                        // Color del texto del titulo (debe contrastar con el fondo)
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,

                        // Color de los iconos (menu, flecha atras)
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),

                    title = {
                        Text(
                            text = stringResource(R.string.titulo_perfil)
                        )
                    },
                    navigationIcon = {
                        FilledIconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            onClick = {
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
                                contentDescription = stringResource(R.string.cd_abrir_menu)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (isLoading || perfil == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                // EXTRAEMOS LOS DATOS COMUNES (UsuarioDTO)
                // Tanto ClienteDTO como TrabajadorDTO tienen un campo 'usuario'
                val usuario =
                    if (esTrabajador) (perfil as TrabajadorDTO).usuario else (perfil as ClienteDTO).usuario

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    item {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // FOTO Y NOMBRE (Comun para ambos)
                            AsyncImage(
                                model = usuario.fotoUrl ?: R.drawable.fotoperfilvacia,
                                contentDescription = stringResource(R.string.cd_foto_perfil),
                                modifier = Modifier
                                    .size(140.dp)
                                    .clip(CircleShape)
                                    .border(2.dp, Color.Gray, CircleShape),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = usuario.nombre,
                                fontSize = 24.sp,
                            )
                            Text(text = usuario.apellidos, fontSize = 20.sp, color = Color.Gray)

                            // ROL
                            AssistChip(
                                onClick = {},
                                label = { Text(usuario.rol.uppercase()) },
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.Person,
                                        null,
                                        tint = MaterialTheme.colorScheme.primary
                                    )
                                },
                                colors = AssistChipDefaults.assistChipColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                                ),
                            )

                            Spacer(modifier = Modifier.height(24.dp))

                            // TARJETA DE DATOS
                            Card(
                                modifier = Modifier.fillMaxWidth(),
                                colors = CardDefaults.cardColors(
                                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                                ),
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    DatoPerfil(
                                        stringResource(R.string.dato_telefono),
                                        usuario.telefono
                                    )
                                    DatoPerfil(stringResource(R.string.dato_email), usuario.email)

                                    // DATOS ESPECIFICOS SEGUN ROL
                                    if (esTrabajador) {
                                        val p = perfil as TrabajadorDTO
                                        DatoPerfil(
                                            stringResource(R.string.dato_radio),
                                            "${p.radioKm} km"
                                        )
                                        DatoPerfil(
                                            stringResource(R.string.dato_descripcion),
                                            p.descripcion
                                                ?: stringResource(R.string.valor_sin_descripcion)
                                        )
                                    } else {
                                        val c = perfil as ClienteDTO
                                        DatoPerfil(
                                            stringResource(R.string.dato_ciudad),
                                            c.ciudad
                                                ?: stringResource(R.string.valor_no_especificada)
                                        )
                                        DatoPerfil(
                                            stringResource(R.string.dato_direccion),
                                            c.direccion
                                                ?: stringResource(R.string.valor_no_especificada)
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

@Composable
fun DatoPerfil(titulo: String, valor: String) {
    Column(modifier = Modifier.padding(vertical = 4.dp)) {
        Text(
            text = titulo,
            fontSize = 14.sp,
            color = Color.Gray
        )
        Text(
            text = valor,
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold
        )
        HorizontalDivider(modifier = Modifier.padding(top = 8.dp))
    }
}


//------------------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscribirResena(
    navController: NavHostController,
    idTrabajador: Int
) {
    val context = LocalContext.current
    val viewModel: CrearResenaViewModel = viewModel(
        factory = CrearResenaViewModelFactory(context)
    )

    // Estados del formulario
    var puntuacion by remember { mutableIntStateOf(0) }
    var comentario by remember { mutableStateOf("") }

    // Estados del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    // Efecto: Si se publica con exito, volvemos atras
    LaunchedEffect(mensajeExito) {
        if (mensajeExito != null) {
            // Esperamos un poquito para que el usuario lea (opcional)
            navController.popBackStack()
            viewModel.resetMensaje()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    // Color de fondo de la barra
                    containerColor = MaterialTheme.colorScheme.primary,

                    // Color del texto del titulo
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,

                    // Color de los iconos (menu, flecha atras)
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),

                title = { Text(stringResource(R.string.titulo_escribir_opinion)) },
                navigationIcon = {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_cancelar)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = stringResource(R.string.texto_que_tal),
                    fontSize = 22.sp,
                    //fontWeight = FontWeight.Bold,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = stringResource(R.string.texto_ayuda_opinion),
                    fontSize = 14.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- COMPONENTE DE ESTRELLAS ---
                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    repeat(5) { index ->
                        val starNumber = index + 1
                        val isSelected = starNumber <= puntuacion

                        Icon(
                            imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.Star,
                            contentDescription = stringResource(R.string.cd_estrella, starNumber),
                            tint = if (isSelected) Color(0xFFFFC107) else Color.LightGray,
                            modifier = Modifier
                                .size(48.dp)
                                .padding(4.dp)
                                .clickable {
                                    puntuacion = starNumber
                                }
                        )
                    }
                }

                Text(
                    text = "$puntuacion/5",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFFC107),
                    modifier = Modifier.padding(top = 8.dp)
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- CAJA DE TEXTO ---
                OutlinedTextField(
                    value = comentario,
                    onValueChange = { comentario = it },
                    label = { Text(stringResource(R.string.label_comentario)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(150.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.weight(1f))

                // --- BOTON DE ENVIAR ---
                Button(
                    onClick = {
                        viewModel.publicarResena(idTrabajador, puntuacion, comentario)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = puntuacion > 0,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.btn_publicar_resena), fontSize = 16.sp)
                }
            }
        }
    }
}


//------------------------------------------------


@Composable
fun VentanaLogin(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))

    // Si el login es exitoso, navegamos a Ofertas y borramos el historial para no volver atras al login
    LaunchedEffect(viewModel.loginExitoso) {
        if (viewModel.loginExitoso) {
            navController.navigate("ofertas") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    Scaffold(
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // --- LOGO O ICONO ---

            Image(
                painter = painterResource(id = R.drawable.logoworknearby),
                contentDescription = stringResource(R.string.cd_logo),
                modifier = Modifier
                    .size(100.dp)
                    .border(3.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = stringResource(R.string.app_name),
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.login_subtitulo),
                fontSize = 16.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- FORMULARIO ---

            // Email
            OutlinedTextField(
                value = viewModel.email,
                onValueChange = { viewModel.email = it },
                label = { Text(stringResource(R.string.label_email)) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Contraseña
            OutlinedTextField(
                value = viewModel.password,
                onValueChange = { viewModel.password = it },
                label = { Text(stringResource(R.string.label_password)) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            // Mensaje de Error
            if (viewModel.loginError != null) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = viewModel.loginError!!,
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BOTON LOGIN
            Button(
                onClick = { viewModel.login() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                shape = RoundedCornerShape(12.dp),
                enabled = !viewModel.isLoading
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text(stringResource(R.string.btn_entrar), fontSize = 18.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // BOTON IR A REGISTRO
            TextButton(
                onClick = { navController.navigate("registro_usuario") }
            ) {
                Text(stringResource(R.string.login_no_cuenta))
            }

        }
    }
}

@Composable
fun VentanaRegistroUsuario(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            contentPadding = PaddingValues(vertical = 24.dp)
        ) {
            item {
                // Header (Logo y Titulos)
                Image(
                    painter = painterResource(id = R.drawable.logoworknearby),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    stringResource(R.string.registro1_titulo),
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    stringResource(R.string.registro1_subtitulo),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))

                // Formulario
                CustomTextField(
                    value = viewModel.nombre,
                    onValueChange = { viewModel.nombre = it },
                    label = stringResource(R.string.label_nombre)
                )
                CustomTextField(
                    value = viewModel.apellidos,
                    onValueChange = { viewModel.apellidos = it },
                    label = stringResource(R.string.label_apellidos)
                )
                CustomTextField(
                    value = viewModel.email,
                    onValueChange = { viewModel.email = it },
                    label = stringResource(R.string.label_email),
                    type = KeyboardType.Email
                )
                CustomTextField(
                    value = viewModel.telefono,
                    onValueChange = { viewModel.telefono = it },
                    label = stringResource(R.string.label_telefono),
                    type = KeyboardType.Phone
                )

                // Password
                OutlinedTextField(
                    value = viewModel.password,
                    onValueChange = { viewModel.password = it },
                    label = { Text(stringResource(R.string.label_password)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp),
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { navController.navigate("escoger_rol") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    enabled = viewModel.nombre.isNotBlank() &&
                            viewModel.apellidos.isNotBlank() &&
                            viewModel.email.isNotBlank() &&
                            viewModel.password.isNotBlank() &&
                            viewModel.telefono.isNotBlank()
                ) {
                    Text(stringResource(R.string.btn_next), fontSize = 18.sp)
                }
            }
        }
    }
}

@Composable
fun VentanaSeleccionRol(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.logoworknearby),
            contentDescription = "Logo",
            modifier = Modifier
                .size(80.dp)
                .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                .clip(CircleShape),
            contentScale = ContentScale.Crop,
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            stringResource(R.string.registro2_titulo),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )
        Text(stringResource(R.string.registro1_subtitulo), fontSize = 14.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(32.dp))

        Spacer(modifier = Modifier.height(40.dp))

        // como Cliente
        RolCard(
            titulo = stringResource(R.string.btn_cliente_titulo),
            subtitulo = stringResource(R.string.btn_cliente_titulo),
            icon = Icons.Default.Search,
            onClick = {
                viewModel.rol = "cliente"
                navController.navigate("registro_cliente")
            }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // como Trabajador
        RolCard(
            titulo = stringResource(R.string.btn_trabajador_titulo),
            subtitulo = stringResource(R.string.btn_trabajador_subtitulo),
            icon = Icons.Default.Build,
            onClick = {
                viewModel.rol = "trabajador"
                navController.navigate("registro_trabajador")
            }
        )
    }
}

@Composable
fun RolCard(titulo: String, subtitulo: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(
                icon,
                contentDescription = null,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(subtitulo, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Composable
fun VentanaRegistroCliente(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {

    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // Estado de la cámara del mapa (Santiago de Compostela por defecto)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(42.8782, -8.5448), 15f)
    }

    // Lanzador para permisos de GPS
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        if (permissions[android.Manifest.permission.ACCESS_FINE_LOCATION] == true) {
            obtenerUbicacionGPS(context, viewModel, cameraPositionState)
        }
    }

    LaunchedEffect(viewModel.registroExitoso) {
        if (viewModel.registroExitoso) {
            navController.navigate("login") {
                popUpTo("registro_usuario") { inclusive = true }
            }
        }
    }

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logoworknearby),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    stringResource(R.string.registro_cli_titulo),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    stringResource(R.string.registro_cli_subtitulo),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))

                // CAMPO DIRECCION
                CustomTextField(
                    value = viewModel.direccion,
                    onValueChange = {
                        viewModel.direccion = it
                        // Al escribir, buscamos en el mapa (opcional poner un debounce aquí)
                        buscarDireccionEnMapa(context, it, cameraPositionState, viewModel)
                    },
                    label = stringResource(R.string.label_direccion)
                )
                Spacer(modifier = Modifier.height(16.dp))

                /*
                CustomTextField(
                    value = viewModel.ciudad,
                    onValueChange = { viewModel.ciudad = it },
                    label = stringResource(R.string.label_ciudad)
                )
                 */

                // BOTON PARA GPS
                OutlinedButton(
                    onClick = {
                        permissionLauncher.launch(
                            arrayOf(
                                android.Manifest.permission.ACCESS_FINE_LOCATION,
                                android.Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Default.LocationOn, contentDescription = null)
                    Spacer(Modifier.width(8.dp))
                    Text("Usar mi ubicación actual (GPS)")
                }

                // VISTA DEL MAPA
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
                ) {
                    GoogleMap(
                        modifier = Modifier.fillMaxSize(),
                        cameraPositionState = cameraPositionState
                    ) {
                        Marker(
                            state = MarkerState(
                                position = LatLng(
                                    viewModel.latitud,
                                    viewModel.longitud
                                )
                            ),
                            title = "Tu ubicación"
                        )
                    }
                }

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.registrarUsuario() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp),
                    //enabled = !viewModel.isLoading,
                    enabled = viewModel.direccion.isNotBlank()
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(stringResource(R.string.btn_finalizar), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

@Composable
fun VentanaRegistroTrabajador(
    navController: NavHostController,
    viewModel: RegistroViewModel
) {

    LaunchedEffect(viewModel.registroExitoso) {
        if (viewModel.registroExitoso) {
            navController.navigate("login") {
                popUpTo("registro_usuario") { inclusive = true }
            }
        }
    }

    Scaffold { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Image(
                    painter = painterResource(id = R.drawable.logoworknearby),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(80.dp)
                        .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    stringResource(R.string.registro_trab_titulo),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    stringResource(R.string.registro_trab_subtitulo),
                    fontSize = 14.sp,
                    color = Color.Gray
                )
                Spacer(modifier = Modifier.height(32.dp))

                OutlinedTextField(
                    value = viewModel.descripcion,
                    onValueChange = { viewModel.descripcion = it },
                    label = { Text(stringResource(R.string.label_desc)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                    maxLines = 5
                )

                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    value = viewModel.radioKm,
                    onValueChange = { viewModel.radioKm = it },
                    label = stringResource(R.string.label_radio),
                    type = KeyboardType.Number
                )

                Spacer(modifier = Modifier.height(32.dp))

                Button(
                    onClick = { viewModel.registrarUsuario() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
                    shape = RoundedCornerShape(12.dp),
                    //enabled = !viewModel.isLoading,
                    enabled = viewModel.descripcion.isNotBlank() && viewModel.radioKm.isNotBlank()
                ) {
                    if (viewModel.isLoading) {
                        CircularProgressIndicator(
                            color = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(stringResource(R.string.btn_finalizar), fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

// FUNCION 1: Buscar lo que el usuario escribe
fun buscarDireccionEnMapa(
    context: Context,
    direccion: String,
    cameraState: CameraPositionState,
    viewModel: RegistroViewModel
) {
    if (direccion.length < 5) return
    val geocoder = Geocoder(context)
    try {
        val direcciones = geocoder.getFromLocationName(direccion, 1)
        if (!direcciones.isNullOrEmpty()) {
            val loc = direcciones[0]
            val latLng = LatLng(loc.latitude, loc.longitude)
            viewModel.latitud = loc.latitude
            viewModel.longitud = loc.longitude
            cameraState.position = CameraPosition.fromLatLngZoom(latLng, 15f)
        }
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

// FUNCION 2: Obtener ubicacion por GPS
@SuppressLint("MissingPermission")
fun obtenerUbicacionGPS(
    context: Context,
    viewModel: RegistroViewModel,
    cameraState: CameraPositionState
) {
    val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
    fusedLocationClient.lastLocation.addOnSuccessListener { location ->
        location?.let {
            viewModel.latitud = it.latitude
            viewModel.longitud = it.longitude
            val latLng = LatLng(it.latitude, it.longitude)
            cameraState.position = CameraPosition.fromLatLngZoom(latLng, 15f)

            // Opcional: Rellenar el texto de dirección automáticamente desde las coordenadas
            val geocoder = Geocoder(context)
            val direcciones = geocoder.getFromLocation(it.latitude, it.longitude, 1)
            if (!direcciones.isNullOrEmpty()) {
                viewModel.direccion = direcciones[0].getAddressLine(0)
                viewModel.ciudad = direcciones[0].locality ?: ""
            }
        }
    }
}

@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    type: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 12.dp),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = type),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}


//------------------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscribirOferta(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: CrearOfertaViewModel = viewModel(
        factory = CrearOfertaViewModelFactory(context)
    )

    // Estados del formulario
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var precioTexto by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) } // La foto seleccionada

    val listaCategorias by viewModel.categorias.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf<CategoriaDTO?>(null) }


    // Estados del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    val launcherImagen = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        fotoUri = uri
    }

    // Efecto: Si se publica con exito, volvemos atras
    LaunchedEffect(mensajeExito) {
        if (mensajeExito != null) {
            // Esperamos un poquito para que el usuario lea (opcional)
            navController.popBackStack()
            viewModel.resetMensaje()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    // Color de fondo de la barra
                    containerColor = MaterialTheme.colorScheme.primary,

                    // Color del texto del titulo
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,

                    // Color de los iconos (menu, flecha atras)
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),

                title = { Text(stringResource(R.string.titulo_nueva_oferta)) },
                navigationIcon = {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_cancelar)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // --- 1. SELECCION DE FOTO ---
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(1.dp, Color.Gray, RoundedCornerShape(12.dp))
                        .clickable {
                            // Abrir galeria (solo imagenes)
                            launcherImagen.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (fotoUri != null) {
                        AsyncImage(
                            model = fotoUri,
                            contentDescription = stringResource(R.string.cd_foto_seleccionada),
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Icon(Icons.Default.Add, contentDescription = null, tint = Color.Gray)
                            Text(
                                stringResource(R.string.text_anadir_foto_opcional),
                                fontSize = 12.sp,
                                color = Color.Gray,
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // --- 2. TITULO ---
                OutlinedTextField(
                    value = titulo,
                    onValueChange = { titulo = it },
                    label = { Text(stringResource(R.string.label_titulo_trabajo)) },
                    placeholder = { Text(stringResource(R.string.placeholder_titulo_trabajo)) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- 3. CATEGORIA---
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = categoriaSeleccionada?.nombre
                            ?: stringResource(R.string.placeholder_seleccionar),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.label_categoria)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    // La lista que se despliega
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (listaCategorias.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.valor_no_especificada)) },
                                onClick = { }
                            )
                        } else {
                            listaCategorias.forEach { categoria ->
                                DropdownMenuItem(
                                    text = { Text(text = categoria.nombre) },
                                    onClick = {
                                        categoriaSeleccionada = categoria
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // --- 4. PRECIO ---
                OutlinedTextField(
                    value = precioTexto,
                    onValueChange = {
                        // Solo numeros y un punto decimal
                        if (it.all { char -> char.isDigit() || char == '.' }) {
                            precioTexto = it
                        }
                    },
                    label = { Text(stringResource(R.string.label_precio_hora_euro)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- 5. DESCRIPCION ---
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text(stringResource(R.string.label_descripcion_detallada)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- 6. BOTON PUBLICAR ---
                Button(
                    onClick = {
                        val precio = precioTexto.toDoubleOrNull() ?: 0.0
                        if (categoriaSeleccionada != null) {
                            viewModel.publicarOferta(
                                titulo,
                                descripcion,
                                precio,
                                categoriaSeleccionada!!.id,
                                fotoUri
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = titulo.isNotEmpty() && descripcion.isNotEmpty() && precioTexto.isNotEmpty() && categoriaSeleccionada != null,
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.btn_publicar_oferta), fontSize = 18.sp)
                }
            }
        }
    }
}


//------------------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscribirContrato(
    navController: NavHostController
) {
    val context = LocalContext.current
    val viewModel: CrearContratoViewModel = viewModel(
        factory = CrearContratoViewModelFactory(context)
    )

    // Estados del formulario
    var emailCliente by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }

    val listaCategorias by viewModel.categorias.collectAsState()
    var expanded by remember { mutableStateOf(false) }
    var categoriaSeleccionada by remember { mutableStateOf<CategoriaDTO?>(null) }

    val listaEstados = listOf(
        stringResource(R.string.estado_pendiente),
        stringResource(R.string.estado_aceptado),
        stringResource(R.string.estado_rechazado)
    )
    var expandedEstado by remember { mutableStateOf(false) }
    var estadoSeleccionado by remember { mutableStateOf("pendiente") }


    // Estados del ViewModel
    val isLoading by viewModel.isLoading.collectAsState()
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    // Efecto: Si se publica con exito, volvemos atras
    LaunchedEffect(mensajeExito) {
        if (mensajeExito != null) {
            // Esperamos un poquito para que el usuario lea (opcional)
            navController.popBackStack()
            viewModel.resetMensaje()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    // Color de fondo de la barra
                    containerColor = MaterialTheme.colorScheme.primary,

                    // Color del texto del titulo
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,

                    // Color de los iconos (menu, flecha atras)
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),

                title = { Text(stringResource(R.string.titulo_nuevo_contrato)) },
                navigationIcon = {
                    FilledIconButton(
                        colors = IconButtonDefaults.filledIconButtonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        ),
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_cancelar)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        if (isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                // --- 1. EMAIL CLIENTE---

                if (viewModel.loginError != null) {
                    Text(
                        text = viewModel.loginError!!,
                        color = MaterialTheme.colorScheme.error,
                        fontSize = 14.sp
                    )
                }

                OutlinedTextField(
                    value = emailCliente,
                    onValueChange = { emailCliente = it },
                    label = { Text(stringResource(R.string.label_email_cliente)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // --- 2. CATEGORIA---
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = { expanded = !expanded },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = categoriaSeleccionada?.nombre
                            ?: stringResource(R.string.placeholder_seleccionar),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.label_categoria)) },
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    // La lista que se despliega
                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        if (listaCategorias.isEmpty()) {
                            DropdownMenuItem(
                                text = { Text(stringResource(R.string.placeholder_cargando)) },
                                onClick = { }
                            )
                        } else {
                            listaCategorias.forEach { categoria ->
                                DropdownMenuItem(
                                    text = { Text(text = categoria.nombre) },
                                    onClick = {
                                        categoriaSeleccionada = categoria
                                        expanded = false
                                    },
                                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                                )
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))

                // --- 3. ESTADO---
                ExposedDropdownMenuBox(
                    expanded = expandedEstado,
                    onExpandedChange = { expandedEstado = !expandedEstado },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = estadoSeleccionado.uppercase(),
                        onValueChange = {},
                        readOnly = true,
                        label = { Text(stringResource(R.string.label_estado_inicial)) },
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedEstado) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(),
                        shape = RoundedCornerShape(12.dp),
                    )

                    ExposedDropdownMenu(
                        expanded = expandedEstado,
                        onDismissRequest = { expandedEstado = false }
                    ) {
                        listaEstados.forEach { estado ->
                            DropdownMenuItem(
                                text = { Text(text = estado.uppercase()) },
                                onClick = {
                                    estadoSeleccionado = estado
                                    expandedEstado = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // --- 4. DESCRIPCION ---
                OutlinedTextField(
                    value = descripcion,
                    onValueChange = { descripcion = it },
                    label = { Text(stringResource(R.string.label_descripcion_trabajo)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp),
                    shape = RoundedCornerShape(12.dp),
                )

                Spacer(modifier = Modifier.height(32.dp))

                // --- 5. BOTON PUBLICAR ---
                Button(
                    onClick = {
                        if (categoriaSeleccionada != null && emailCliente.isNotEmpty()) {
                            viewModel.publicarContrato(
                                emailCliente,
                                categoriaSeleccionada!!.id,
                                descripcion,
                                estadoSeleccionado
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = emailCliente.isNotEmpty() && categoriaSeleccionada != null && descripcion.isNotEmpty(),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Text(stringResource(R.string.btn_crear_contrato), fontSize = 18.sp)
                }
            }
        }
    }
}


//------------------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialNotificaciones(navController: NavHostController) {
    val context = LocalContext.current
    val viewModel: NotificacionesViewModel = viewModel(
        factory = NotificacionesViewModelFactory(context)
    )

    val listaNotificaciones by viewModel.notificaciones.collectAsState()
    val nombre by viewModel.nombreUsuario.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()


    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = stringResource(R.string.menu_hola, nombre),
                    modifier = Modifier.padding(15.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider()
                Spacer(modifier = Modifier.height(15.dp))

                NavigationDrawerItem(
                    label = { Text(stringResource(R.string.menu_perfil)) },
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
                    label = { Text(text = stringResource(R.string.menu_ofertas)) },
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
                    label = { Text(stringResource(R.string.menu_contratos)) },
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
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        // Color de fondo de la barra
                        containerColor = MaterialTheme.colorScheme.primary,

                        // Color del texto del titulo
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,

                        // Color de los iconos
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    ),

                    title = {
                        Text(
                            text = stringResource(R.string.titulo_notificaciones)
                        )
                    },
                    navigationIcon = {
                        FilledIconButton(
                            colors = IconButtonDefaults.filledIconButtonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer
                            ),
                            onClick = {
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
                                contentDescription = stringResource(R.string.cd_abrir_menu)
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            if (isLoading) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (listaNotificaciones.isEmpty()) {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = stringResource(R.string.msg_no_hay_notificaciones))
                }
            } else {
                ListaNotificaciones(
                    notificaciones = listaNotificaciones,
                    modifier = Modifier.padding(paddingValues)
                )
            }
        }
    }
}

@Composable
fun ListaNotificaciones(notificaciones: List<NotificacionDTO>, modifier: Modifier = Modifier) {
    LazyColumn(modifier
        .fillMaxSize()
        .padding(top = 10.dp)) {
        items(notificaciones) { notificacion ->
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer,
                    contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                ),
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Icono de campana a la izquierda
                    Icon(
                        imageVector = Icons.Default.Notifications,
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.width(16.dp))

                    Column {
                        Text(
                            text = notificacion.titulo,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = notificacion.mensaje,
                            fontSize = 14.sp,
                            color = Color.DarkGray
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = notificacion.fechaEnvio ?: "--/--/--",
                            fontSize = 12.sp,
                            color = Color.Gray
                        )
                    }
                }
            }
        }
    }
}


//------------------------------------------------


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EscribirNotificacion(
    navController: NavHostController,
    idUsuarioDestino: Int // ID del usuario que recibirá la notificación
) {
    val context = LocalContext.current
    val viewModel: NotificacionesViewModel = viewModel(
        factory = NotificacionesViewModelFactory(context)
    )

    var titulo by remember { mutableStateOf("") }
    var mensaje by remember { mutableStateOf("") }
    val mensajeExito by viewModel.mensajeExito.collectAsState()

    LaunchedEffect(mensajeExito) {
        if (mensajeExito != null) {
            // Esperamos un poquito para que el usuario lea (opcional)
            navController.popBackStack()
            viewModel.resetMensaje()
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Nueva Notificación") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.cd_cancelar)
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // --- 1. TITULO ---
            OutlinedTextField(
                value = titulo,
                onValueChange = { titulo = it },
                label = { Text(stringResource(R.string.label_titulo_mensaje)) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(20.dp))

            // --- 2. MENSAJE (Multiline como la descripción del contrato) ---
            OutlinedTextField(
                value = mensaje,
                onValueChange = { mensaje = it },
                label = { Text(stringResource(R.string.label_mensaje)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                shape = RoundedCornerShape(12.dp),
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(32.dp))

            // --- 3. BOTÓN ENVIAR ---
            Button(
                onClick = {
                    if (titulo.isNotEmpty() && mensaje.isNotEmpty()) {
                        viewModel.enviarNotificacion(idUsuarioDestino, titulo, mensaje)
                        navController.popBackStack()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                enabled = titulo.isNotEmpty() && mensaje.isNotEmpty(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(Icons.Default.Send, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.cd_notificar), fontSize = 18.sp)
            }
        }
    }
}
