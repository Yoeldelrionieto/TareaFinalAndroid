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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import com.composse.tareafinal.viewmodel.AnimeJikanViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

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
    trailerUrl: String?
) {
    val context = LocalContext.current

    TareaFinalTheme {
        Surface {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = title ?: "Sin título")
                Spacer(modifier = Modifier.height(8.dp))
                if (!posterImage.isNullOrEmpty()) {
                    Image(
                        painter = rememberImagePainter(posterImage),
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(300.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Calificación: ${rating ?: "N/A"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Duración de episodios: ${duration ?: "Desconocida"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Emitido: ${aired ?: "Desconocido"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Géneros: ${genres ?: "Desconocido"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Estudios: ${studios ?: "Desconocido"}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Productores: ${producers ?: "Desconocido"}")
                Spacer(modifier = Modifier.height(8.dp))
                Button(
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl ?: ""))
                        context.startActivity(intent)
                    },
                    modifier = Modifier.padding(top = 16.dp)
                ) {
                    Text("Ver Tráiler")
                }
            }
        }
    }
}
