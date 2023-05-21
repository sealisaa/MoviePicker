package com.example.moviepicker.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.service.KPApiService
import com.example.moviepicker.data.model.movie.FilmItem
import io.reactivex.disposables.CompositeDisposable

class MoviesByFiltersDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable,
    private val countryId: Int? = null,
    private val genreId: Int? = null,
    private val rating: Int? = null,
    private val yearFrom: Int? = null,
    private val yearTo: Int? = null,
    private val keyword: String? = null
) : DataSource.Factory<Int, FilmItem>() {

    val filmLiveDataSource = MutableLiveData<MovieByFiltersDataSource>()

    override fun create(): DataSource<Int, FilmItem> {
        val popularFilmDataSource = MovieByFiltersDataSource(apiService, compositeDisposable, keyword = keyword,
            rating = rating,
            yearFrom = yearFrom,
            yearTo = yearTo,
            countryId = countryId,
            genreId = genreId)

        filmLiveDataSource.postValue(popularFilmDataSource)
        return popularFilmDataSource
    }
}