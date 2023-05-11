package com.example.moviepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import api.model.top.movie.TopItem
import api.service.*
import api.service.repository.FilmDataSource
import api.service.repository.FilmDataSourceFactory
import api.service.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviePagedListRepository<T : Any>(private val apiService: KPApiService) {

    lateinit var filmPagedList: LiveData<PagedList<T>>
    lateinit var filmDataSourceFactory: FilmDataSourceFactory<T>

    fun fetchLiveMoviePagedList(compositeDisposable: CompositeDisposable): LiveData<PagedList<T>> {
        filmDataSourceFactory = FilmDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        filmPagedList = LivePagedListBuilder(filmDataSourceFactory, config).build()
        return filmPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<FilmDataSource<T>, NetworkState>(
            filmDataSourceFactory.filmLiveDataSource, FilmDataSource<T>::networkState
        )
    }
}