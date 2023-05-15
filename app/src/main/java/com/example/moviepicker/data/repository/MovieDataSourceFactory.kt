package com.example.moviepicker.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.service.KPApiService
import com.example.moviepicker.data.model.top.movie.TopItem
import com.example.moviepicker.data.model.top.movie.TopType
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable,
    private val topType: TopType
) : DataSource.Factory<Int, TopItem>() {

    val filmLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, TopItem> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable, topType)

        filmLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}