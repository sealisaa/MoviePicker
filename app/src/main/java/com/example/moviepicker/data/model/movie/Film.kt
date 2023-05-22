package com.example.moviepicker.data.model.movie

import com.example.moviepicker.data.model.common.Country
import com.example.moviepicker.data.model.common.Genre
import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
data class Film(
    val kinopoiskId: Int,
    val imdbId: String?,
    var nameRu: String?,
    val nameEn: String?,
    val posterUrl: String?,
    val posterUrlPreview: String?,
    val ratingKinopoisk: Double?,
    val ratingImdb: Double?,
    val ratingFilmCritics: Double?,
    val year: Int?,
    val description: String?,
    val countries: List<Country>,
    val genres: List<Genre>,
    )
