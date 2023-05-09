package data.model.search.movie.keyword

import data.model.common.Country
import data.model.common.Genre
import data.model.common.KinopoiskItemType
import com.fasterxml.jackson.annotation.JsonProperty

data class SearchItem(
    @JsonProperty("filmId")
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val type: KinopoiskItemType = KinopoiskItemType.UNKNOWN,
    val year: String?,
    val description: String?,
    val filmLength: String?,
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val rating: String?,
    val ratingVoteCount: Int?,
    val posterUrl: String,
    val posterUrlPreview: String
)
