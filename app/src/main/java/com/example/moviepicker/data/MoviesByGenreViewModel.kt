package com.example.moviepicker.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import com.example.moviepicker.MoviesByFiltersPagedListRepository
import com.example.moviepicker.data.model.movie.FilmItem
import com.example.moviepicker.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesByGenreViewModel(
    private val filmRepository: MoviesByFiltersPagedListRepository, countryId: Int? = null,
    genreId: Int? = null,
    rating: Int? = null,
    yearFrom: Int? = null,
    yearTo: Int? = null,
    keyword: String? = null
) :
    ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<FilmItem>> by lazy {
        filmRepository.fetchLiveMoviePagedList(compositeDisposable, keyword = keyword,
            rating = rating,
            yearFrom = yearFrom,
            yearTo = yearTo,
            countryId = countryId,
            genreId = genreId)
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