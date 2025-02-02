package com.composse.tareafinal.view

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.BoxScopeInstance.align
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import com.composse.tareafinal.viewmodel.AnimeJikanViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.composse.tareafinal.R

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun DetailsAnimeScreen(
    navController: NavController,
    title: String?,
    posterImage: String?,
    rating: Double?,
    duration: String?,
    aired: String?,
    genres: String?,
    studios: String?,
    producers: String?,
    trailerUrl: String?,
    episodes: Int?,
    synopsis: String?   // Mantenemos 'synopsis' si lo usas
) {
    val context = LocalContext.current

    // Definir las familias de fuentes
    val cocogooseFontFamily = FontFamily(
        Font(R.font.coolvetica_rg)
    )

    val monoRegularFontFamily = FontFamily(
        Font(R.font.mono_regular)
    )

    TareaFinalTheme {
        Surface {
            // Hacemos que el contenido sea scrollable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Título del anime
                Text(
                    text = title ?: "Sin título",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    fontFamily = cocogooseFontFamily
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Imagen de portada
                if (!posterImage.isNullOrEmpty()) {
                    Image(
                        painter = rememberImagePainter(posterImage),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

                // Información del anime con fuentes específicas
                InfoRow(label = "Calificación:", info = "${rating ?: "N/A"}", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)
                InfoRow(label = "Episodios:", info = "${episodes ?: "Desconocido"}", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)
                InfoRow(label = "Duración:", info = duration ?: "Desconocida", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)
                InfoRow(label = "Emitido:", info = aired ?: "Desconocido", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)
                InfoRow(label = "Géneros:", info = genres ?: "Desconocido", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)
                InfoRow(label = "Estudios:", info = studios ?: "Desconocido", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)
                InfoRow(label = "Productores:", info = producers ?: "Desconocido", labelFont = monoRegularFontFamily, infoFont = cocogooseFontFamily)

                Spacer(modifier = Modifier.height(16.dp))

                // Sinopsis
                if (!synopsis.isNullOrEmpty()) {
                    Text(
                        text = "Sinopsis:",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        fontFamily = monoRegularFontFamily
                    )
                    Text(
                        text = synopsis,
                        textAlign = TextAlign.Justify,
                        modifier = Modifier.padding(top = 8.dp),
                        fontFamily = cocogooseFontFamily
                    )

                    Spacer(modifier = Modifier.height(16.dp))
                }

                // Botón para ver el tráiler
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl ?: ""))
                        context.startActivity(intent)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                ) {
                    Text(
                        text = "Ver Tráiler",
                        fontFamily = monoRegularFontFamily,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Función auxiliar para mostrar información con fuentes específicas
// Función auxiliar para mostrar la información con fuentes específicas y opacidad en las etiquetas
@Composable
fun InfoRow(label: String, info: String, labelFont: FontFamily, infoFont: FontFamily) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = label,
            fontFamily = labelFont,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp,
            color = LocalContentColor.current.copy(alpha = 0.5f)
        )
        Text(
            text = info,
            fontFamily = infoFont,
            fontSize = 16.sp
            // El texto de 'info' permanece con su opacidad normal
        )
        Spacer(modifier = Modifier.height(8.dp))
    }
}

