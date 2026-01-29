package com.raul_fernandez_garcia.worknearby.pruebas

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.raul_fernandez_garcia.worknearby.LoginViewModel
import com.raul_fernandez_garcia.worknearby.LoginViewModelFactory
import com.raul_fernandez_garcia.worknearby.R

@Preview(showBackground = true)
@Composable
fun VentanaSeleccionRol() {

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("¡Bienvenido!", fontSize = 28.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Text("¿Cómo quieres usar WorkNearby?", fontSize = 16.sp, color = Color.Gray)

        Spacer(modifier = Modifier.height(40.dp))

        // como Cliente
        RolCard(
            titulo = "Busco Servicios",
            subtitulo = "Quiero contratar profesionales",
            icon = Icons.Default.Search,
            onClick = {  }
        )

        Spacer(modifier = Modifier.height(20.dp))

        // como Trabajador
        RolCard(
            titulo = "Ofrezco Servicios",
            subtitulo = "Quiero trabajar y ganar dinero",
            icon = Icons.Default.Build,
            onClick = { }
        )
    }
}

@Composable
fun RolCard(titulo: String, subtitulo: String, icon: ImageVector, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth().clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Row(modifier = Modifier.padding(24.dp), verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, modifier = Modifier.size(40.dp), tint = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(titulo, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(subtitulo, fontSize = 14.sp, color = Color.Gray)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun VentanaRegistroCliente() {
    // Implementar estados (nombre, email, password, direccion, ciudad...)

    var nombre = ""
    var apellidos = ""
    var email = ""
    var password = ""
    var direccion = ""
    var ciudad = ""

    LazyColumn(modifier = Modifier.fillMaxSize().padding(24.dp)) {
        item {
            Text("Registro Cliente", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(20.dp))

            // Reutilizamos el estilo de tus OutlinedTextFields

            CustomTextField(value = nombre, onValueChange = { nombre = it }, label = "Nombre")
            CustomTextField(value = apellidos, onValueChange = { apellidos = it }, label = "Apellidos")
            CustomTextField(value = email, onValueChange = { email = it }, label = "Email", type = KeyboardType.Email)
            CustomTextField(value = password, onValueChange = { password = it }, label = "Contraseña", isPassword = true)

            Divider(modifier = Modifier.padding(vertical = 16.dp))
            Text("Ubicación para entregas", fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(value = direccion, onValueChange = { direccion = it }, label = "Dirección")
            CustomTextField(value = ciudad, onValueChange = { ciudad = it }, label = "Ciudad")

            Button(
                onClick = { /* Lógica registrar cliente */ },
                modifier = Modifier.fillMaxWidth().padding(top = 24.dp).height(50.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text("Finalizar Registro")
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
        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp),
        singleLine = true,
        shape = RoundedCornerShape(12.dp),
        keyboardOptions = KeyboardOptions(keyboardType = type),
        visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None
    )
}

@Preview(showBackground = true)
@Composable
fun VentanaLogin() {
    val context = LocalContext.current
    val viewModel: LoginViewModel = viewModel(factory = LoginViewModelFactory(context))

    // Si el login es exitoso, navegamos a Ofertas y borramos el historial para no volver atras al login
    LaunchedEffect(viewModel.loginExitoso) {
        if (viewModel.loginExitoso) {
            //navController.navigate("ofertas") { popUpTo("login") { inclusive = true } }
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
                onClick = { /*navController.navigate("registro_usuario")*/ }
            ) {
                Text(stringResource(R.string.login_no_cuenta))
            }

        }
    }
}
