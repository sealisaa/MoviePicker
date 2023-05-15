package com.example.moviepicker.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.service.KPApiService
import com.example.moviepicker.data.model.movie.FilmItem
import io.reactivex.disposables.CompositeDisposable

class MoviesByFiltersDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, FilmItem>() {

    val filmLiveDataSource = MutableLiveData<MovieByFiltersDataSource>()

    override fun create(): DataSource<Int, FilmItem> {
        val popularFilmDataSource = MovieByFiltersDataSource(apiService, compositeDisposable)

        filmLiveDataSource.postValue(popularFilmDataSource)
        return popularFilmDataSource
    }
}