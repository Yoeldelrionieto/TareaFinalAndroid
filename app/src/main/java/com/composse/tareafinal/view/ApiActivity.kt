package com.composse.tareafinal.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.composse.tareafinal.viewmodel.UserViewModel
import kotlin.system.exitProcess
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import androidx.compose.material3.*
import androidx.compose.ui.platform.LocalContext

@Composable
fun ApiScreen(navController: NavController, viewModel: UserViewModel) {
    // Cuando el usuario consulta la API, se debe detener la notificación periódica
    (LocalContext.current as MainActivity).stopPeriodicNotification()
    val users by viewModel.users.collectAsState(initial = emptyList())
    TareaFinalTheme {
        Surface() {
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
                        Surface(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            shape = RoundedCornerShape(8.dp),
                            border = BorderStroke(1.dp, Color.Gray),

                        ) {
                            Column(modifier = Modifier.padding(16.dp)) {
                                Text(
                                    text = buildAnnotatedString {
                                        append("Nombre: ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(user.name)
                                        }
                                    },
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Justify
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        append("Correo: ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(user.email)
                                        }
                                    },
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Justify
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(
                                    text = buildAnnotatedString {
                                        append("Ciudad: ")
                                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                            append(user.address.city)
                                        }
                                    },
                                    style = MaterialTheme.typography.body1,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.Justify
                                )
                            }
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
                        onClick = { navController.popBackStack() },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Volver")
                    }
                }
            }
        }
    }


}
fun closeApp() {
    exitProcess(0)
}



