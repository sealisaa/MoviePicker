package api.service

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.PagedList
import api.model.movie.FilmItem
import api.model.top.movie.TopItem
import api.service.repository.NetworkState
import com.example.moviepicker.MoviesByFiltersPagedListRepository
import com.example.moviepicker.PopularMoviePagedListRepository
import io.reactivex.disposables.CompositeDisposable

class MoviesByGenreViewModel(private val filmRepository: MoviesByFiltersPagedListRepository) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val moviePagedList: LiveData<PagedList<FilmItem>> by lazy {
        filmRepository.fetchLiveMoviePagedList(compositeDisposable)
    }

    val networkState: LiveData<NetworkState> by lazy {
        filmRepository.getNetworkState()
    }

    fun listIsEmpty(): Boolean {
        return moviePagedList.value?.isEmpty() ?: true
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}