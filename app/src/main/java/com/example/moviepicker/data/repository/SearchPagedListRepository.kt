package com.example.moviepicker.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import api.service.KPApiService
import api.service.POST_PER_PAGE
import com.example.moviepicker.data.model.search.movie.keyword.SearchItem
import io.reactivex.disposables.CompositeDisposable

class SearchPagedListRepository(private val apiService: KPApiService) {

    lateinit var filmPagedList: LiveData<PagedList<SearchItem>>
    lateinit var searchDataSourceFactory: SearchDataSourceFactory

    fun fetchLiveSearchPagedList(
        compositeDisposable: CompositeDisposable,
        keyword: String
    ): LiveData<PagedList<SearchItem>> {
        searchDataSourceFactory = SearchDataSourceFactory(apiService, compositeDisposable, keyword)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        filmPagedList = LivePagedListBuilder(searchDataSourceFactory, config).build()
        return filmPagedList

    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<SearchDataSource, NetworkState>(
            searchDataSourceFactory.filmLiveDataSource, SearchDataSource::networkState
        )
    }





}