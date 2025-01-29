package com.composse.tareafinal.network

import com.composse.tareafinal.model.AnimeResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.Response
import retrofit2.http.Query

interface ApiJikanService {
    /**@GET("anime")
    suspend fun getAnimes(): Response<AnimeResponse>*/
    @GET("anime")
    suspend fun getAnimes(@Query("page") page: Int): Response<AnimeResponse>
}

object RetrofitInstancia {
    val api: ApiJikanService by lazy {
        Retrofit.Builder()
            .baseUrl("https://api.jikan.moe/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiJikanService::class.java)
    }
}
