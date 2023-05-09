package data.api

import androidx.lifecycle.LiveData
import data.model.movie.Film
import data.repository.MovieDetailsNetworkDataSource
import data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

const val API_KEY = "4522d8bc-6718-4f11-b449-7b37cdff4577"
object DBClient {

    fun getClient() : KPApiService = KPApiService(API_KEY)
}

class FilmDetailsRepository (private val apiService: KPApiService) {

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