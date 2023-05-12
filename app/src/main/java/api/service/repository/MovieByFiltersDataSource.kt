package api.service.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import api.model.movie.FilmItem
import api.service.FIRST_PAGE
import api.service.KPApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MovieByFiltersDataSource(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) :
    PageKeyedDataSource<Int, FilmItem>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, FilmItem>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getFilmsByFileters(genreId = 1, page = params.key)
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
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            apiService.getFilmsByFileters(genreId = 1, page = page)
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