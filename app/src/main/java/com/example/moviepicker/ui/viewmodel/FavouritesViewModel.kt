package com.example.moviepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.repository.FavouriteMoviesRepository
import io.reactivex.disposables.CompositeDisposable

class FavouritesViewModel(
    private val filmRepository: FavouriteMoviesRepository,
    private val _savedMoviesId: MutableList<Int> = mutableListOf()
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    private var _favouriteMovies: MutableLiveData<MutableList<Film>> =
        filmRepository.fetchMovieDetails(compositeDisposable, _savedMoviesId)
    val favouriteMovies: LiveData<MutableList<Film>>
        get() = _favouriteMovies

    val savedMoviesId: List<Int>
        get() = _savedMoviesId

    var lastItemDeleted: Int = -1

    fun saveMovie(movieId: Int) {
        this._savedMoviesId.add(movieId)
        filmRepository.fetchMovieDetails(compositeDisposable, _savedMoviesId)
        _favouriteMovies = filmRepository.fetchMovieDetails(compositeDisposable, _savedMoviesId)

    }

    fun deleteMovie(movieId: Int) {
        lastItemDeleted = savedMoviesId.indexOf(movieId)
        this._savedMoviesId.remove(movieId)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}