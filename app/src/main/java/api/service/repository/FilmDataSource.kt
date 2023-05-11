package api.service.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import api.model.top.movie.TopItem
import api.model.top.movie.TopResult
import api.model.top.movie.TopType
import api.service.FIRST_PAGE
import api.service.KPApiService
import api.service.genericApi
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class FilmDataSource<T : Any>(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) :
    PageKeyedDataSource<Int, T>() {

    private var page = FIRST_PAGE

    //    private var genericApi = genericApi()
    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {

        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            foo(params, callback)!!
        )
    }

    fun foo(params: LoadParams<Int>, callback: LoadCallback<Int, T>): Disposable? {
        val t = Any() as T
        when (t) {
            is TopItem -> {
                return apiService.getTop(TopType.TOP_250_BEST_FILMS, params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if (it.pagesCount >= params.key) {
                                callback.onResult(it.films as List<T>, params.key + 1)
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
            }

            else -> {
                return apiService.getTop(TopType.TOP_250_BEST_FILMS, params.key)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            if (it.pagesCount >= params.key) {
                                callback.onResult(it.films as List<T>, params.key + 1)
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
            }
        }

    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, T>) {
        TODO("Not yet implemented")
    }

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>) {
        networkState.postValue(NetworkState.LOADING)
        compositeDisposable.add(
            foo(params, callback)!!
        )
    }

    fun foo(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, T>): Disposable? {
        val e:T
        e = Any() as T
        val d: Disposable
        when (e) {
            is TopItem -> {
                return apiService.getTop(TopType.TOP_250_BEST_FILMS, page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.films as List<T>, null, page + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            it.message?.let { it1 -> Log.e("FilmDataSource", it1) }
                        }
                    )
            }
            else -> {
                return apiService.getTop(TopType.TOP_250_BEST_FILMS, page)
                    .subscribeOn(Schedulers.io())
                    .subscribe(
                        {
                            callback.onResult(it.films as List<T>, null, page + 1)
                            networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            networkState.postValue(NetworkState.ERROR)
                            it.message?.let { it1 -> Log.e("FilmDataSource", it1) }
                        }
                    )
            }
        }

    }
}