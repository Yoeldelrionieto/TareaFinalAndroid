package com.composse.tareafinal.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composse.tareafinal.model.AnimeJikan
import com.composse.tareafinal.model.AnimeResponse
import com.composse.tareafinal.network.RetrofitInstancia
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Response

class AnimeJikanViewModel : ViewModel() {
    private val _animes = MutableStateFlow<List<AnimeJikan>>(emptyList())
    val animes: StateFlow<List<AnimeJikan>> = _animes

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private var currentPage = 1
    private var lastVisiblePage = 1

    init {
        fetchAnimes()
    }

    fun fetchAnimes() {
        if (_isLoading.value || currentPage > lastVisiblePage) return

        _isLoading.value = true
        viewModelScope.launch {
            try {
                val response: Response<AnimeResponse> = RetrofitInstancia.api.getAnimes(currentPage)
                if (response.isSuccessful) {
                    response.body()?.let { animeResponse ->
                        lastVisiblePage = animeResponse.pagination?.last_visible_page ?: 1
                        val animeDataList = animeResponse.data ?: emptyList()
                        val animeList = animeDataList.map { animeData ->
                            AnimeJikan(
                                id = animeData.mal_id,
                                title = animeData.title,
                                synopsis = animeData.synopsis ?: "Sin sinopsis disponible",
                                rating = animeData.score,
                                posterImage = animeData.images?.jpg?.image_url,
                                url = animeData.url,
                                trailerUrl = animeData.trailer?.url,
                                genres = animeData.genres?.map { it.name },
                                producers = animeData.producers?.map { it.name },
                                studios = animeData.studios?.map { it.name },
                                duration = animeData.duration,
                                aired = animeData.aired?.string,
                                episodes = animeData.episodes,                          // Mapeo del campo episodes
                                background = animeData.background ?: "No disponible",   // Mapeo del campo background
                                relatedAnimes = animeData.relations?.flatMap { relation ->
                                    relation.entry?.map { it.name ?: "Desconocido" } ?: emptyList()
                                },
                                coverImage = animeData.images?.jpg?.large_image_url,    // Mapeo del cover image
                                backgroundImage = animeData.images?.jpg?.image_url      // Mapeo del background image
                            )
                        }
                        _animes.value = _animes.value + animeList
                        currentPage++
                    }
                } else {
                    Log.e("AnimeViewModel", "Error en la respuesta: ${response.message()}")
                }
            } catch (e: Exception) {
                Log.e("AnimeViewModel", "Error al obtener animes: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    // Método para obtener los detalles de un anime específico
    fun getAnimeById(animeId: Int): AnimeJikan? {
        return _animes.value.find { it.id == animeId }
    }
}
