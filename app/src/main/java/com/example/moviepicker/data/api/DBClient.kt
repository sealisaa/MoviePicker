package com.example.moviepicker.data.api

import androidx.lifecycle.LiveData
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.repository.MovieDetailsNetworkDataSource
import com.example.moviepicker.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

const val API_KEY = "4522d8bc-6718-4f11-b449-7b37cdff4577"
object DBClient {

    fun getClient() : com.example.moviepicker.data.api.KPApiService =
        com.example.moviepicker.data.api.KPApiService(com.example.moviepicker.data.api.API_KEY)
}

class FilmDetailsRepository (private val apiService: com.example.moviepicker.data.api.KPApiService) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchFilmDetails (compositeDisposable: CompositeDisposable, filmId: Int) : LiveData<Film> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchFilmDetails(filmId)

        return movieDetailsNetworkDataSource.downloadedFilmResponse
    }

    fun getFilmDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}