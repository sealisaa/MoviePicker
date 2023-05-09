package com.example.moviepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import data.api.FilmDetailsRepository
import data.model.movie.Film
import data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MovieViewModel(private val filmRepository: FilmDetailsRepository, filmId: Int) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val movieDetails: LiveData<Film> by lazy {
        filmRepository.fetchFilmDetails(compositeDisposable, filmId)
    }

    val networkState: LiveData<NetworkState> by lazy {
        filmRepository.getFilmDetailsNetworkState()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}