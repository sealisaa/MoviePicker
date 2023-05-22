package com.example.moviepicker.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import api.service.FIRST_PAGE
import api.service.KPApiService
import com.example.moviepicker.data.model.search.movie.keyword.SearchItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class SearchDataSource(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable,
    private val keyword: String
) : PageKeyedDataSource<Int, SearchItem>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.searchByKeyword(keyword, params.key)
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
                        it.message?.let { it1 -> Log.e("SearchDataSource", it1) }
                    }
                )
        )
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, SearchItem>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, SearchItem>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.searchByKeyword(keyword, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.films, null, page + 1)
                        networkState.postValue(NetworkState.LOADED)
                    },
                    {
                        networkState.postValue(NetworkState.ERROR)
                        it.message?.let { it1 -> Log.e("SearchDataSource", it1) }
                    }
                )
        )
    }
}