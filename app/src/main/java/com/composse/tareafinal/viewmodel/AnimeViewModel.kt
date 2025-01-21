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
        fetchAnimes() // Obtener solo 10 animes
    }

    private fun fetchAnimes() {
        viewModelScope.launch {
            try {
                val response: Response<AnimeResponse> = RetrofitInstancia.api.getAnimes()
                if (response.isSuccessful) {
                    response.body()?.data?.let { animeDataList ->
                        val animeList = animeDataList.mapNotNull { animeData ->
                            try {
                                Anime(
                                    id = animeData.id ?: "",
                                    title = animeData.attributes?.canonicalTitle ?: "Título no disponible",
                                    synopsis = animeData.attributes?.synopsis ?: "Sinopsis no disponible",
                                    rating = animeData.attributes?.averageRating?.toDoubleOrNull() ?: 0.0,
                                    posterImage = animeData.attributes?.posterImage?.large ?: "",
                                    coverImage = animeData.attributes?.coverImage?.large ?: ""
                                )
                            } catch (e: Exception) {
                                Log.e("AnimeViewModel", "Error al procesar anime: ${animeData.id}, ${e.message}")
                                null
                            }
                        }
                        Log.d("AnimeViewModel", "Animes obtenidos: $animeList")
                        _animes.value = animeList
                    }
                } else {
                    Log.e("AnimeViewModel", "Error al obtener animes: ${response.message()}")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("AnimeViewModel", "Error al obtener animes: ${e.message}")
            }
        }
    }
}
