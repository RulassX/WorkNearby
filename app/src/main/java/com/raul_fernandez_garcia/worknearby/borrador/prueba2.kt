package com.raul_fernandez_garcia.worknearby.borrador

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raul_fernandez_garcia.worknearby.R
import com.raul_fernandez_garcia.worknearby.ui.theme.WorkNearbyTheme
import kotlinx.coroutines.launch

class prueba2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface {
                appWorkNearby()
            }
        }
    }
}

@Composable
private fun appWorkNearby() {
    WorkNearbyTheme {
        Surface() {
            BuscarOfertas()
            //BuscarContratos()
            //TrabajoOfertado()
            //Perfil()
            //BuscarChats()
        }
    }
}


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
private fun BuscarOfertas() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val listaOfertasReal = listOf("Raúl", "Brais", "Laura", "Carlos", "Lucia", "Pedro", "Maria")
    val apellidos =
        listOf("Fernández García", "Fernández", "Gomez", "Varela", "Rodriguez", "Lopez", "García")
    //val puntuaciones = listOf("5/5", "4/5", "3.5/5", "2/5", "3/5", "2.5/5", "4.5/5")


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(text = "OP1", modifier = Modifier.padding(16.dp))
                Text(text = "OP2", modifier = Modifier.padding(16.dp))
                Icon(imageVector = Icons.Default.Search, contentDescription = null)
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
                    modifier = Modifier.padding(paddingValues)
                )
            }

            Button(onClick = {
                //navController.navigate("contratos")
            }) {
                Text(text = "Ir a contratos")
            }
        }
    }
}


@Composable
private fun ListaOfertas(modifier: Modifier = Modifier) {

    val nombres = listOf("Raúl", "Brais", "Laura", "Carlos", "Lucia", "Pedro", "Maria")
    val apellidos =
        listOf("Fernández García", "Fernández", "Gomez", "Varela", "Rodriguez", "Lopez", "García")
    //val puntuaciones = listOf("5/5", "4/5", "3.5/5", "2/5", "3/5", "2.5/5", "4.5/5")


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
                            text = "Pintor",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, top = 15.dp)
                        )

                        Text(
                            text = nombre + " " + apellido,
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(bottom = 15.dp, start = 15.dp),
                            //textAlign = TextAlign.Center,
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "Precio: " + "30" + "€/h",
                            fontSize = 17.sp,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 15.dp),
                        )
                    }

                    Image(
                        painter = painterResource(R.drawable.imagenvacia),
                        contentDescription = "imagen",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(115.dp) // Ancho fijo para la imagen
                        ,
                        contentScale = ContentScale.Crop // Recorta la imagen para llenar el espacio
                    )


                }
            }
        }
    }
}

/*
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

 */