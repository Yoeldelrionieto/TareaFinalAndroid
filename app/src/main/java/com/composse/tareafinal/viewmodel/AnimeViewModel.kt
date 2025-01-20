package com.composse.tareafinal.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composse.tareafinal.model.Anime
import com.composse.tareafinal.network.RetrofitInstancia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import android.util.Log
import com.composse.tareafinal.model.AnimeResponse
import retrofit2.Response

class AnimeViewModel : ViewModel() {
    private val _animes = MutableStateFlow<List<Anime>>(emptyList())
    val animes: StateFlow<List<Anime>> = _animes

    init {
        fetchAnime("1") // Ejemplo para obtener el primer anime
    }

    private fun fetchAnime(animeId: String) {
        viewModelScope.launch {
            try {
                val response: Response<AnimeResponse> = RetrofitInstancia.api.getAnime(animeId)
                if (response.isSuccessful) {
                    val animeData = response.body()?.data
                    if (animeData != null) {
                        val anime = Anime(
                            id = animeData.id,
                            title = animeData.attributes.canonicalTitle,
                            synopsis = animeData.attributes.synopsis,
                            rating = animeData.attributes.averageRating?.toDoubleOrNull() ?: 0.0,
                            posterImage = animeData.attributes.posterImage.large,
                            coverImage = animeData.attributes.coverImage.large
                        )
                        Log.d("AnimeViewModel", "Anime obtenido: $anime")
                        _animes.value = listOf(anime)
                    }
                } else {
                    Log.e("AnimeViewModel", "Error al obtener anime: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AnimeViewModel", "Error al obtener anime: ${e.message}")
            }
        }
    }
}

