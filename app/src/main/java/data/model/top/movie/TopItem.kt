package data.model.top.movie

import data.model.common.Country
import data.model.common.Genre
import com.fasterxml.jackson.annotation.JsonProperty

data class TopItem(
    @JsonProperty("filmId")
    val kinopoiskId: Int,
    val nameRu: String?,
    val nameEn: String?,
    val year: String?,
    val filmLength: String?,
    val countries: List<Country> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val rating: String?, // can be percent if film has not been released
    val ratingVoteCount: Int?,
    val posterUrl: String,
    val posterUrlPreview: String,
    val ratingChange: Int?,
    val isRatingUp: Int?,
    val isAfisha: Int
)