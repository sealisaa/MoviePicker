package com.example.moviepicker.data.api

import androidx.lifecycle.LiveData
import api.service.KPApiService
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.repository.MovieDetailsNetworkDataSource
import com.example.moviepicker.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

//const val API_KEY = "4522d8bc-6718-4f11-b449-7b37cdff4577"
//const val API_KEY = "0fe9cc9b-1012-4d3c-b32b-9aa7f17c22fa"
const val API_KEY = "dfbdfa94-a2b5-4126-9924-2e27afe8a20b"
object DBClient {

    fun getClient(): KPApiService =
        KPApiService(API_KEY)
}

class FilmDetailsRepository(private val apiService: KPApiService) {

    lateinit var movieDetailsNetworkDataSource: MovieDetailsNetworkDataSource

    fun fetchFilmDetails (compositeDisposable: CompositeDisposable, movieId: Int) : LiveData<Film> {

        movieDetailsNetworkDataSource = MovieDetailsNetworkDataSource(apiService, compositeDisposable)
        movieDetailsNetworkDataSource.fetchMovieDetails(movieId)

        return movieDetailsNetworkDataSource.downloadedMovieResponse
    }

    fun getFilmDetailsNetworkState(): LiveData<NetworkState> {
        return movieDetailsNetworkDataSource.networkState
    }
}