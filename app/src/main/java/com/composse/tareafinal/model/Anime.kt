package com.composse.tareafinal.model

data class Anime(
    val id: String,
    val title: String?,
    val synopsis: String?,
    val rating: Double?,
    val posterImage: String?,
    val coverImage: String?
)

data class AnimeResponse(
    val data: List<AnimeData>?
)

data class AnimeData(
    val id: String?,
    val attributes: AnimeAttributes?
)

data class AnimeAttributes(
    val canonicalTitle: String?,
    val synopsis: String?,
    val averageRating: String?,
    val posterImage: Image?,
    val coverImage: Image?
)

data class Image(
    val large: String?
)



