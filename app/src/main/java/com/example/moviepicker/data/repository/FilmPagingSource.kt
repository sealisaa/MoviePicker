package com.example.moviepicker.data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.moviepicker.data.model.top.movie.TopItem
import com.example.moviepicker.data.model.top.movie.TopType
import com.example.moviepicker.data.api.FIRST_PAGE
import com.example.moviepicker.data.api.KPApiService
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FilmPagingSource(private val apiService: KPApiService, private val compositeDisposable: CompositeDisposable) :
    PagingSource<Int, TopItem>() {

    private var page = FIRST_PAGE

    val networkState: MutableLiveData<NetworkState> = MutableLiveData()

    override fun getRefreshKey(state: PagingState<Int, TopItem>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TopItem> {
        networkState.postValue(NetworkState.LOADING)

        when (params){
            is LoadParams.Prepend -> {
            }

            is LoadParams.Refresh, is LoadParams.Append -> {

            }
        }


        compositeDisposable.add(
            apiService.getTop(TopType.TOP_250_BEST_FILMS, page)
                .subscribeOn(Schedulers.io())
                .subscribe(
                    {
                        if (it.pagesCount >= params.key!!) {
                            networkState.postValue(NetworkState.LOADED)
                        }

                    },
                    {
                        networkState.postValue(NetworkState.ENDOFLIST)
                    }
                )
        )
//        return LoadResult.Page(it.films, params.key + 1)
        return LoadResult.Page(emptyList(), null, null)
    }

}