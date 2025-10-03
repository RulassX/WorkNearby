package com.raul_fernandez_garcia.worknearby

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raul_fernandez_garcia.worknearby.ui.theme.WorkNearbyTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Surface { }
        }
    }
}

@Composable
private fun app() {
    WorkNearbyTheme {
        Surface() {
            Content()

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
private fun Content() {
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
            ListaOfertas(modifier = Modifier.padding(paddingValues))

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
                            text = "Valoracion: " + "5/5",
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