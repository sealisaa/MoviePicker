package api.service

import androidx.lifecycle.LiveData
import api.model.movie.Film
import api.service.repository.FilmDetailsNetworkDataSource
import api.service.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

const val API_KEY = "4522d8bc-6718-4f11-b449-7b37cdff4577"
object DBClient {

    fun getClient() : KPApiService = KPApiService(API_KEY)
}

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