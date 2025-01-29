package com.composse.tareafinal.model

data class AnimeJikan(
    val id: Int,
    val title: String?,
    val synopsis: String?,
    val rating: Double?,
    val posterImage: String?,
    val url: String?,
    val trailerUrl: String?, // URL del tráiler
    val genres: List<String>?, // Lista de géneros
    val producers: List<String>?, // Lista de productores
    val studios: List<String>?, // Lista de estudios
    val duration: String?, // Duración de episodios
    val aired: String? // Fecha de emisión
)

data class AnimeResponse(
    val pagination: Pagination?,
    val data: List<AnimeData>?
)

data class Pagination(
    val last_visible_page: Int?,
    val has_next_page: Boolean?,
    val current_page: Int?,
    val items: PaginationItems?
)

data class PaginationItems(
    val count: Int?,
    val total: Int?,
    val per_page: Int?
)


data class AnimeData(
    val mal_id: Int,
    val title: String,
    val synopsis: String?,
    val score: Double?,
    val images: AnimeImages?,
    val url: String?,
    val trailer: AnimeTrailer?,
    val genres: List<AnimeGenre>?,
    val producers: List<AnimeProducer>?,
    val studios: List<AnimeStudio>?,
    val duration: String?,
    val aired: Aired?
)

data class AnimeImages(
    val jpg: ImageDetails?
)

data class ImageDetails(
    val image_url: String?
)

data class AnimeTrailer(
    val url: String?
)

data class AnimeGenre(
    val name: String
)

data class AnimeProducer(
    val name: String
)

data class AnimeStudio(
    val name: String
)

data class Aired(
    val string: String?
)
