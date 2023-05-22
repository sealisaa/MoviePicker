package com.example.moviepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviepicker.data.model.search.movie.keyword.SearchItem
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.data.repository.SearchPagedListRepository
import io.reactivex.disposables.CompositeDisposable

class SearchResultViewModel(private val filmRepository: SearchPagedListRepository, private val keyword: String) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<SearchItem>> by lazy {
        filmRepository.fetchLiveSearchPagedList(compositeDisposable, keyword)
    }

    val networkState: LiveData<NetworkState> by lazy {
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