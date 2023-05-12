package com.example.moviepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import api.model.top.movie.TopItem
import api.service.*
import api.service.repository.PopularFilmDataSource
import api.service.repository.PopularFilmsDataSourceFactory
import api.service.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class PopularMoviePagedListRepository(private val apiService: KPApiService) {

    lateinit var filmPagedList: LiveData<PagedList<TopItem>>
    lateinit var popularFilmsDataSourceFactory: PopularFilmsDataSourceFactory

    fun fetchLiveMoviePagedList (compositeDisposable: CompositeDisposable) : LiveData<PagedList<TopItem>> {
        popularFilmsDataSourceFactory = PopularFilmsDataSourceFactory(apiService, compositeDisposable)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        filmPagedList = LivePagedListBuilder(popularFilmsDataSourceFactory, config).build()
        return filmPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<PopularFilmDataSource, NetworkState>(
            popularFilmsDataSourceFactory.filmLiveDataSource, PopularFilmDataSource::networkState
        )
    }
}