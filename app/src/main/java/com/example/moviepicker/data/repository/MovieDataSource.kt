package com.example.moviepicker.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.example.moviepicker.data.model.top.movie.TopItem
import com.example.moviepicker.data.model.top.movie.TopType
import com.example.moviepicker.data.api.FIRST_PAGE
import com.example.moviepicker.data.api.KPApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieDataSource(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable,
    private val topType: TopType
) :
    PageKeyedDataSource<Int, TopItem>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, TopItem>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTop(topType, params.key)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.pagesCount >= params.key) {
                            callback.onResult(it.films, params.key + 1)
                            networkState.postValue(NetworkState.LOADED)
                        } else {
                            networkState.postValue(NetworkState.ENDOFLIST)
                        }
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("FilmDataSource", it1) }
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, TopItem>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, TopItem>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getTop(topType, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.films, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("FilmDataSource", it1) }
                    }
                )
        )
    }
}