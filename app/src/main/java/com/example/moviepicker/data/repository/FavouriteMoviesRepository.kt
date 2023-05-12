package com.example.moviepicker.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import api.service.KPApiService
import com.example.moviepicker.data.model.movie.Film
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class FavouriteMoviesRepository(private val apiService: KPApiService) {

    fun fetchMovieDetails(
        compositeDisposable: CompositeDisposable,
        savedMoviesId: List<Int>
    ): MutableLiveData<MutableList<Film>> {
        Log.d("FavouriteMoviesRepository", savedMoviesId.size.toString())
        val downloadedFilmResponse = MutableLiveData<MutableList<Film>>()
        try {
            compositeDisposable.add(
                apiService.getFilms(savedMoviesId)
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            downloadedFilmResponse.postValue(it)
                        },
                        {
                            Log.e("FavouriteMoviesRepository", "getFilms returned null")
                        }
                    )
            )
        } catch (e: Exception) {
            e.message?.let { Log.e("ApiNetworkExecutor", it) }
        }
        return downloadedFilmResponse
    }
}