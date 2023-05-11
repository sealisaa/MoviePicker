package api.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.model.top.movie.TopItem
import api.service.KPApiService
import io.reactivex.disposables.CompositeDisposable

class FilmDataSourceFactory<T : Any>(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, T>() {

    val filmLiveDataSource = MutableLiveData<FilmDataSource<T>>()

    override fun create(): DataSource<Int, T> {
        val filmDataSource = FilmDataSource<T>(apiService, compositeDisposable)

        filmLiveDataSource.postValue(filmDataSource)
        return filmDataSource
    }
}