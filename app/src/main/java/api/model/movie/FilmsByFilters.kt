package api.model.movie

import api.model.common.Country
import api.model.common.Genre
import api.model.common.KinopoiskItemType
import com.fasterxml.jackson.annotation.JsonIgnore

data class FilmsByFilters(
    val total: Int?,
    val totalPages: Int?,
    val items: List<FilmItem> = emptyList()
)

data class FilmItem(
    val kinopoiskId: Int,
    val imdbId: String?,
    var nameRu: String?,
    var nameEn: String?,
    val nameOriginal: String?,
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val ratingKinopoisk: Double,
    val ratingImdb: Double,
    val year: String?,
    var type: KinopoiskItemType,
    val posterUrl: String?,
    val posterUrlPreview: String?,
)
