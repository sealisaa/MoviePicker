package api.service

import androidx.lifecycle.LiveData
import api.model.movie.Film
import io.reactivex.disposables.CompositeDisposable

class FilmDetailsRepository (private val apiService: KPApiService) {

    lateinit var filmDetailsNetworkDataSource: FilmDetailsNetworkDataSource

    fun fetchFilmDetails (compositeDisposable: CompositeDisposable, filmId: Int) : LiveData<Film> {

        filmDetailsNetworkDataSource = FilmDetailsNetworkDataSource(apiService, compositeDisposable)
        filmDetailsNetworkDataSource.fetchFilmDetails(filmId)

        return filmDetailsNetworkDataSource.downloadedFilmResponse
    }

    fun getFilmDetailsNetworkState(): LiveData<NetworkState> {
        return filmDetailsNetworkDataSource.networkState
    }
}