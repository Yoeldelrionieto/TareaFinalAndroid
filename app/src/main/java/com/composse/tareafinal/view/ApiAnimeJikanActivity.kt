package com.composse.tareafinal.view

import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import com.composse.tareafinal.viewmodel.AnimeJikanViewModel
//import com.composse.tareafinal.viewmodel.AnimeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.googlefonts.GoogleFont
import androidx.compose.ui.unit.sp
import com.composse.tareafinal.R
import java.net.URLEncoder


@Composable
fun ApiAnimeJikanScreen(navController: NavController, viewModel: AnimeJikanViewModel = viewModel()) {
    (LocalContext.current as MainActivity).stopPeriodicNotification()
    val animes by viewModel.animes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    val customFont = FontFamily(
        Font(R.font.coolvetica_rg)
    )
    TareaFinalTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "Datos de la API de Jikan",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    textAlign = TextAlign.Center
                )

                if (animes.isEmpty() && !isLoading) {
                    Text(
                        text = "No hay datos disponibles!",
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3),
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .padding(bottom = 1.dp)
                    ) {
                        items(animes) { anime ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(1.dp),
                                onClick = {
                                    navController.navigate(
                                        "details/${anime.id}?title=${Uri.encode(anime.title)}" +
                                                "&posterImage=${Uri.encode(anime.posterImage ?: "")}" +
                                                "&rating=${anime.rating ?: "N/A"}" +
                                                "&duration=${Uri.encode(anime.duration ?: "Desconocida")}" +
                                                "&aired=${Uri.encode(anime.aired ?: "Desconocido")}" +
                                                "&genres=${Uri.encode(anime.genres?.joinToString() ?: "Desconocido")}" +
                                                "&studios=${Uri.encode(anime.studios?.joinToString() ?: "Desconocido")}" +
                                                "&producers=${Uri.encode(anime.producers?.joinToString() ?: "Desconocido")}" +
                                                "&trailerUrl=${Uri.encode(anime.trailerUrl ?: "")}" +
                                                "&episodes=${anime.episodes ?: -1}" +
                                                "&synopsis=${Uri.encode(anime.synopsis ?: "Sin sinopsis disponible")}"
                                    )
                                }

                            ) {
                                Column {
                                    if (!anime.posterImage.isNullOrEmpty()) {
                                        Image(
                                            painter = rememberImagePainter(anime.posterImage),
                                            contentDescription = null,
                                            modifier = Modifier
                                                .fillMaxSize()
                                                .height(200.dp)
                                                .aspectRatio(3f / 4f)
                                        )
                                    }
                                    Text(
                                        text = formatTitle(anime.title ?: "Sin título"),
                                        modifier = Modifier
                                            .fillMaxSize(),
                                        fontFamily = customFont,
                                        fontSize = 20.sp,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }

                        item {
                            if (isLoading) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                )
                            } else {
                                Button(
                                    onClick = { viewModel.fetchAnimes() },
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                        .align(Alignment.CenterHorizontally)
                                ) {
                                    Text("+")
                                }
                            }
                        }
                    }
                }


            }
        }
    }
}

fun formatTitle(title: String): String {
    return if (title.length > 18) {
        title.take(15) + "..."
    } else {
        title
    }
}
