package com.example.moviepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import api.model.movie.FilmItem
import api.service.*
import api.service.repository.MovieByFiltersDataSource
import api.service.repository.MoviesByFiltersDataSourceFactory
import api.service.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesByFiltersPagedListRepository(private val apiService: KPApiService) {

    lateinit var filmPagedList: LiveData<PagedList<FilmItem>>
    lateinit var popularFilmsDataSourceFactory: MoviesByFiltersDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<FilmItem>> {
        popularFilmsDataSourceFactory = MoviesByFiltersDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        filmPagedList = LivePagedListBuilder(popularFilmsDataSourceFactory, config).build()
        return filmPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieByFiltersDataSource, NetworkState>(
            popularFilmsDataSourceFactory.filmLiveDataSource, MovieByFiltersDataSource::networkState
        )
    }
}