package com.raul_fernandez_garcia.worknearby.presentacion

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.raul_fernandez_garcia.worknearby.ui.theme.WorkNearbyTheme
import kotlinx.coroutines.launch

/*
@Preview
@Composable
private fun PantalalPrueba() {
    Surface (modifier = Modifier.fillMaxSize()){
        Text(text = "hola")

    }
}


@Composable
private fun WorkApp() {
    WorkNearbyTheme {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
            Greeting(
                name = "Android", modifier = Modifier.padding(innerPadding)
            )
        }
    }
}

@Composable
private fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!", modifier = modifier

    )
}

@Preview(showBackground = true)
@Composable
private fun GreetingPreview() {
    WorkNearbyTheme {
        Greeting("Android")
    }
}


//-------------------------------------
//-------------------------------------

/*@Preview(showBackground = false)
@Composable
fun ViewContainer() {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = {Content(), modifier = Modifier.padding(it)}
    )
}

@Preview(showBackground = true)
@Composable
fun Toolbar(){
    TopAppBar(title = { Text(text = "WorkNearby")})
}
@Preview(showBackground = false)
@Composable
fun Content() {

}*/*/

@OptIn(ExperimentalMaterial3Api::class)
//@Preview(showBackground = false)
@Composable
fun Content() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Drawer lateral
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Opción 1", modifier = Modifier.padding(16.dp))
                Text("Opción 2", modifier = Modifier.padding(16.dp))
                Text("Opción 3", modifier = Modifier.padding(16.dp))
            }
        }
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("WorkNearby") },
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
                                contentDescription = "Abrir menú"
                            )
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
                    .background(Color.Blue),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "hola",
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun ListaDeNombres() {
    LazyColumn(
        modifier = Modifier
            .background(Color.Blue)
            .fillMaxSize()
    ) {
        item {
            Text(
                text = "Hola",
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

        }
        item {
            Text(
                text = "Adios",
                fontSize = 28.sp,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        }

        items(5) { index ->
            Text(text = "Item: $index")
        }
    }
}

@Preview
@Composable
private fun ListaOfertas() {
    LazyColumn(
        modifier = Modifier
            .background(Color.Gray)
            .fillMaxSize()
            .padding(PaddingValues())
            .padding(top = 10.dp)
    ) {
        items(7) { index ->

            Card(
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ),
                modifier = Modifier
                    .height(150.dp)
                    .padding(vertical = 10.dp, horizontal = 15.dp)
                    .fillMaxWidth()

            ) {
                Text(
                    text = "Item: $index",
                    fontSize = 28.sp,
                    modifier = Modifier.fillMaxWidth(),
                    //textAlign = TextAlign.Center
                )
            }
        }

    }
}