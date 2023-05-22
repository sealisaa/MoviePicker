package com.example.moviepicker.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.service.KPApiService
import com.example.moviepicker.data.model.search.movie.keyword.SearchItem
import io.reactivex.disposables.CompositeDisposable

class SearchDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable,
    private val keyword: String
) : DataSource.Factory<Int, SearchItem>() {

    val filmLiveDataSource = MutableLiveData<SearchDataSource>()

    override fun create(): DataSource<Int, SearchItem> {
        val searchDataSource = SearchDataSource(apiService, compositeDisposable, keyword)

        filmLiveDataSource.postValue(searchDataSource)
        return searchDataSource
    }

}