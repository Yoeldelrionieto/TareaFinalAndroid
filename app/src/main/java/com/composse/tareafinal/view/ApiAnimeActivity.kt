package com.composse.tareafinal.view

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import com.composse.tareafinal.viewmodel.AnimeViewModel
//import kotlinx.coroutines.flow.collectAsState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState

@Composable
fun ApiAnimeScreen(navController: NavController, viewModel: AnimeViewModel = viewModel()) {
    val anime by viewModel.animes.collectAsState()
    TareaFinalTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Datos de la API", modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp), textAlign = TextAlign.Center)

                if (anime.isEmpty()) {
                    Text(text = "No hay datos disponibles!", modifier = Modifier.align(Alignment.CenterHorizontally))
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                            .padding(bottom = 16.dp)
                    ) {
                        items(anime) { anime ->
                            Surface(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                                    .border(BorderStroke(1.dp, Color.Gray)),
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Text(
                                        text = buildAnnotatedString {
                                            append("Título: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(anime.title)
                                            }
                                        },
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Justify
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = buildAnnotatedString {
                                            append("Sinopsis: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(anime.synopsis)
                                            }
                                        },
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Justify
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = buildAnnotatedString {
                                            append("Calificación: ")
                                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                                append(anime.rating.toString())
                                            }
                                        },
                                        style = MaterialTheme.typography.body1,
                                        modifier = Modifier.fillMaxWidth(),
                                        textAlign = TextAlign.Justify
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Image(
                                        painter = rememberImagePainter(anime.posterImage),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth().height(200.dp)
                                    )
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Image(
                                        painter = rememberImagePainter(anime.coverImage),
                                        contentDescription = null,
                                        modifier = Modifier.fillMaxWidth().height(200.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}