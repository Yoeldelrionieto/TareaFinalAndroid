package com.composse.tareafinal.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composse.tareafinal.viewmodel.UserViewModel
import kotlin.system.exitProcess

@Composable
fun ApiScreen(navController: NavController, viewModel: UserViewModel) {
    val users by viewModel.users.collectAsState(initial = emptyList())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Datos de la API",
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(bottom = 16.dp)
        ) {
            items(users) { user ->
                Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Nombre: ${user.name}")
                        Text(text = "Correo: ${user.email}")
                        Text(text = "Ciudad: ${user.address.city}")
                }
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { closeApp() },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cerrar App")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { navController.navigate("login") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Volver")
            }
        }
    }
}
fun closeApp() {
    exitProcess(0)
}



