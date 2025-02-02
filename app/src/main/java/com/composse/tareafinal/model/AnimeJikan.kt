package com.composse.tareafinal.model

data class AnimeJikan(
    val id: Int,
    val title: String?,
    val synopsis: String?,
    val rating: Double?,
    val posterImage: String?,
    val url: String?,
    val trailerUrl: String?,
    val genres: List<String>?,
    val producers: List<String>?,
    val studios: List<String>?,
    val duration: String?,
    val aired: String?,
    val episodes: Int?,
    val background: String?,
    val relatedAnimes: List<String>?,
    val coverImage: String?,
    val backgroundImage: String?
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
    val aired: Aired?,
    val episodes: Int?,
    val background: String?,
    val relations: List<Relation>?
)


data class AnimeImages(
    val jpg: ImageDetails?,
    val webp: ImageDetails?
)

data class ImageDetails(
    val image_url: String?,
    val large_image_url: String?,
    val small_image_url: String?
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
data class RelatedAnimeEntry(
    val mal_id: Int,
    val type: String?,
    val name: String?,
    val url: String?
)

data class Relation(
    val relation: String?,
    val entry: List<RelatedAnimeEntry>?
)

