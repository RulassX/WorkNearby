package com.raul_fernandez_garcia.worknearby

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.raul_fernandez_garcia.worknearby.modeloDTO.OfertaDTO
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

        composable("trabajo_ofertado") {
            TrabajoOfertado(navController)
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
    navController: NavHostController,
    viewModel: OfertasViewModel = viewModel()
) {
    val listaOfertasReal by viewModel.ofertas.collectAsState()

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
                    modifier = Modifier.padding(paddingValues)
                )
            }

            Button(onClick = {
                navController.navigate("contratos")
            }) {
                Text(text = "Ir a contratos")
            }
        }
    }
}

@Composable
fun ListaOfertas(ofertas: List<OfertaDTO>, modifier: Modifier = Modifier) {
    //val nombres = listOf()
    //val apellidos = listOf()
    //val puntuaciones = listOf()

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
                            text = "Nombre: ${oferta.nombreTrabajador}",
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
                        model = oferta.fotoUrlOferta ?: R.drawable.ic_launcher_background, // Si es null, usa icono por defecto
                        contentDescription = "Foto de oferta",
                        modifier = Modifier
                            .padding(15.dp)
                            .width(130.dp) // Ancho fijo para la imagen
                            .fillMaxHeight(),
                        contentScale = ContentScale.Crop // Recorta la imagen para llenar el espacio
                    )
                }
            }
        }
    }
}

class OfertasViewModel : ViewModel() {
    private val _ofertas = MutableStateFlow<List<OfertaDTO>>(emptyList())
    val ofertas: StateFlow<List<OfertaDTO>> = _ofertas

    init {
        cargarOfertas()
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
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun BuscarContratos(navController: NavHostController) {
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
            ListaContratos(modifier = Modifier.padding(paddingValues))
        }
    }
}

@Composable
private fun ListaContratos(modifier: Modifier = Modifier) {
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
                    .height(150.dp)
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
                            text = "Nombre: " + nombre,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 15.dp),
                            //textAlign = TextAlign.Center,
                        )
                        Text(
                            text = "Apellidos: " + apellido,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 5.dp),
                            //textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Trabajo: " + "Pintor",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 15.dp),
                            textAlign = TextAlign.End,
                        )
                    }
                    Image(
                        modifier = Modifier
                            .padding(15.dp),
                        painter = painterResource(id = R.drawable.ic_launcher_background),
                        contentDescription = "imagen",
                    )
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TrabajoOfertado(navController: NavHostController) {
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
            LazyColumn(
                modifier = Modifier
                    .background(Color.Gray)
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                item {
                    Row {
                        Column {
                            Text(
                                text = "Raúl",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(top = 15.dp, bottom = 5.dp, start = 15.dp)
                            )
                            Text(
                                text = "Fernández García",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(bottom = 15.dp, start = 15.dp)
                            )
                            Text(
                                text = "Pintor",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(bottom = 15.dp, start = 15.dp)
                            )
                            Text(
                                text = "Telf.: 123456789",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(bottom = 15.dp, start = 15.dp)
                            )
                            Text(
                                text = "Email: ejemplo@gmail.com",
                                fontSize = 22.sp,
                                modifier = Modifier
                                    .padding(bottom = 15.dp, start = 15.dp)
                            )
                        }

                        Image(
                            modifier = Modifier
                                .padding(15.dp)
                                .fillMaxWidth(),
                            painter = painterResource(id = R.drawable.ic_launcher_background),
                            contentDescription = "imagen",
                            alignment = Alignment.TopEnd
                        )
                    }
                    Text(
                        text = "Descripcion trabajador (Opcional)",
                        fontSize = 22.sp,
                        modifier = Modifier
                            .padding(15.dp)
                            .padding(top = 15.dp)
                    )
                }
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