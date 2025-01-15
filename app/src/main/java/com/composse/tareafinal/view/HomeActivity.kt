package com.composse.tareafinal.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composse.tareafinal.ui.theme.TareaFinalTheme

@Composable
fun HomeScreen(navController: NavController, userId: String, name: String, accessCount: Int) {
    TareaFinalTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Bienvenido, $name")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Has accedido $accessCount veces")
                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = { navController.navigate("api") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Consultar API")
                }
            }
        }
    }

}
