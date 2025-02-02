package com.composse.tareafinal.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.googlefonts.GoogleFont


import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.composse.tareafinal.R
import com.composse.tareafinal.ui.theme.TareaFinalTheme


@Composable
fun HomeScreen(navController: NavController, userId: String, name: String, accessCount: Int) {
    var showImageOptions by remember { mutableStateOf(false) }
    var selectedImage by remember { mutableIntStateOf(R.drawable.profile_placeholder) }

    val customFont = FontFamily(
        Font(R.font.mono_regular)
    )
    // Determinar si el tema es oscuro o claro
    val isDarkTheme = isSystemInDarkTheme()

    // Seleccionar la imagen de fondo adecuada
    val backgroundImage = if (isDarkTheme) {
        painterResource(id = R.drawable.background_noir) // Imagen para modo oscuro
    } else {
        painterResource(id = R.drawable.background_light) // Imagen para modo claro
    }
    TareaFinalTheme {
        // Usamos Box para apilar la imagen de fondo y el contenido
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop, // Ajusta la imagen para llenar el área
                modifier = Modifier.fillMaxSize()
            )
            Surface(
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Top,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(8.dp),
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            // Aquí aplicamos los cambios
                            Box(
                                modifier = Modifier
                                    .size(60.dp), // Aseguramos que el Box tenga el mismo tamaño que la imagen
                                contentAlignment = Alignment.BottomEnd // Movemos el IconButton a la esquina inferior derecha
                            ) {
                                Image(
                                    painter = painterResource(id = selectedImage),
                                    contentDescription = "Imagen de perfil",
                                    modifier = Modifier
                                        .fillMaxSize() // La imagen llena todo el espacio del Box
                                        .clip(CircleShape)
                                        .border(2.dp, Color.Gray, CircleShape)
                                )
                                IconButton(
                                    onClick = { showImageOptions = !showImageOptions },
                                    modifier = Modifier
                                        .size(24.dp) // Ajustamos el tamaño del IconButton si es necesario
                                ) {
                                    Icon(Icons.Default.Edit, contentDescription = "Cambiar imagen")
                                }
                            }
                            if (showImageOptions) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    listOf(
                                        R.drawable.profile_placeholder,
                                        R.drawable.images2,
                                        R.drawable.images3
                                    ).forEach { imageRes ->
                                        Image(
                                            painter = painterResource(id = imageRes),
                                            contentDescription = "Imagen alternativa",
                                            modifier = Modifier
                                                .size(40.dp)
                                                .clip(RoundedCornerShape(4.dp))
                                                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                                                .clickable {
                                                    selectedImage = imageRes
                                                    showImageOptions = false
                                                }
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = name,
                                fontFamily = customFont,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Veces accedidas: $accessCount",
                                fontSize = 16.sp,
                                fontFamily = customFont
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { navController.navigate("api") },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Consultar API", fontFamily = customFont)
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate("apiAnime") },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Consultar API Anime", fontFamily = customFont)
                    }
                }
            }
        }
    }
}
