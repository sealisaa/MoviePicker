package data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import data.model.movie.Film
import data.api.KPApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers


class MovieDetailsNetworkDataSource(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) {

    private val _networkState = MutableLiveData<NetworkState>()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _downloadedFilmResponse = MutableLiveData<Film>()
    val downloadedFilmResponse: LiveData<Film>
        get() = _downloadedFilmResponse

    fun fetchFilmDetails(kinopoisId: Int) {

        _networkState.postValue(NetworkState.LOADING)

        try {
            compositeDisposable.add(
                apiService.getFilm(kinopoisId)
                    .observeOn(Schedulers.io())
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {
                            _downloadedFilmResponse.postValue(it)
                            _networkState.postValue(NetworkState.LOADED)
                        },
                        {
                            _networkState.postValue(NetworkState.ERROR)
                        }
                    )
            )
        } catch (e: Exception) {
            e.message?.let { Log.e("ApiNetworkExecutor", it) }
        }
    }
}

