package com.raul_fernandez_garcia.worknearby.borrador

import android.R.attr.contentDescription
import android.annotation.SuppressLint
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import coil.compose.AsyncImage
import com.raul_fernandez_garcia.worknearby.ListaOfertas
import com.raul_fernandez_garcia.worknearby.R
import kotlinx.coroutines.launch

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
                        painter = painterResource(R.drawable.fotoPerfilVacia),
                        contentDescription = "imagen",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(100.dp) // Ancho fijo para la imagen
                            ,
                        contentScale = ContentScale.Crop // Recorta la imagen para llenar el espacio
                    )


                }
            }
        }
    }
}