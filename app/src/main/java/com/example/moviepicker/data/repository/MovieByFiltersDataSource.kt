package com.example.moviepicker.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import api.service.FIRST_PAGE
import api.service.KPApiService
import com.example.moviepicker.data.model.movie.FilmItem
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieByFiltersDataSource(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable,
    private val countryId: Int? = null,
    private val genreId: Int? = null,
    private val rating: Int? = null,
    private val yearFrom: Int? = null,
    private val yearTo: Int? = null,
    private val keyword: String? = null
) :
    PageKeyedDataSource<Int, FilmItem>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FilmItem>) {
        Log.e("genreId", "inside request we have " + genreId.toString())
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getFilmsByFilters(
                countryId = countryId,
                genreId = genreId,
                ratingFrom = rating,
                ratingTo = 10,
                yearFrom = yearFrom,
                yearTo = yearTo,
                keyword = keyword,
                page = params.key
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.totalPages!! >= params.key) {
                            callback.onResult(it.items, params.key + 1)
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


    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, FilmItem>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, FilmItem>) {
        Log.e("genreId", "inside request we have " + genreId.toString())
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getFilmsByFilters(
                countryId = countryId,
                genreId = genreId,
                ratingFrom = rating,
                ratingTo = 10,
                yearFrom = yearFrom,
                yearTo = yearTo,
                keyword = keyword,
                page = page
            )
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        callback.onResult(it.items, null, page + 1)
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