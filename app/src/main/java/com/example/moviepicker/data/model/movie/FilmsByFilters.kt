package com.example.moviepicker.data.model.movie

import com.example.moviepicker.data.model.common.Country
import com.example.moviepicker.data.model.common.Genre
import com.example.moviepicker.data.model.common.KinopoiskItemType

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
