package com.example.moviepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviepicker.data.model.top.movie.TopItem
import com.example.moviepicker.data.model.top.movie.TopType
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.ui.MoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class PopularMoviesViewModel(private val filmRepository: MoviePagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList : LiveData<PagedList<TopItem>> by lazy {
        filmRepository.fetchLiveMoviePagedList(compositeDisposable, TopType.TOP_100_POPULAR_FILMS)
    }

    val networkState : LiveData<NetworkState> by lazy {
        filmRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}