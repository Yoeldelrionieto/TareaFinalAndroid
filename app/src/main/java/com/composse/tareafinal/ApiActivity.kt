package com.composse.tareafinal

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Composable
fun ApiScreen(navController: NavController) {
    /*val users = remember { mutableStateOf(listOf<User>()) }

    // Llamada a la API (se puede hacer en un ViewModel para mejor organización)
    LaunchedEffect(Unit) {
        val apiService = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
            users.value = apiService.getUsers()
    }*/

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Datos de la API")
        Spacer(modifier = Modifier.height(16.dp))

        /*LazyColumn {
            items(users.value) { user ->
                Text("Nombre: ${user.name}")
                Text("Correo: ${user.email}")
                Text("Ciudad: ${user.address.city}")
                Spacer(modifier = Modifier.height(8.dp))
            }
        }*/

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Cerrar la aplicación */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Cerrar App")
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { navController.navigate("home") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Volver")
            }
        }
    }
}
