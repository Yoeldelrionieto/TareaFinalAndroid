package com.composse.tareafinal.network

import com.composse.tareafinal.model.Anime
import com.composse.tareafinal.model.AnimeResponse
import com.composse.tareafinal.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response

interface ApiMangaService {
    @GET("anime/{id}")
    suspend fun getAnime(@Path("id") animeId: String): Response<AnimeResponse>
}

object RetrofitInstancia {
    val api: ApiMangaService by lazy {
        Retrofit.Builder()
            .baseUrl("https://kitsu.io/api/edge/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiMangaService::class.java)
    }
}


