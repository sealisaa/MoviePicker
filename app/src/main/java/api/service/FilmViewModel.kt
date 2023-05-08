package api.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import api.model.movie.Film
import api.service.FilmDetailsRepository
import api.service.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class FilmViewModel(private val filmRepository: FilmDetailsRepository, filmId: Int) : ViewModel() {

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