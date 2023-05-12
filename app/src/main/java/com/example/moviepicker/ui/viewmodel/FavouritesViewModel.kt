package com.example.moviepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviepicker.data.api.FilmDetailsRepository
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.repository.FavouriteMoviesRepository
import io.reactivex.disposables.CompositeDisposable

class FavouritesViewModel(
    private val filmRepository: FavouriteMoviesRepository,
    private val savedMoviesId: MutableList<Int> = mutableListOf()
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    // todo make private
//    val favouriteMovies: LiveData<List<Film>> by lazy {
//        filmRepository.fetchMovieDetails(compositeDisposable, savedMoviesId)
//    }

    private var _favouriteMovies: MutableLiveData<MutableList<Film>> =
        filmRepository.fetchMovieDetails(compositeDisposable, savedMoviesId)
    val favouriteMovies: LiveData<MutableList<Film>>
        get() = _favouriteMovies

    fun saveMovie(movieId: Int) {
        this.savedMoviesId.add(movieId)
        filmRepository.fetchMovieDetails(compositeDisposable, savedMoviesId)
        _favouriteMovies = filmRepository.fetchMovieDetails(compositeDisposable, savedMoviesId)

    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}