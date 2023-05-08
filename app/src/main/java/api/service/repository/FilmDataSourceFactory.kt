package api.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.model.top.movie.TopItem
import api.service.KPApiService
import io.reactivex.disposables.CompositeDisposable

class FilmDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, TopItem>() {

    val filmLiveDataSource = MutableLiveData<FilmDataSource>()

    override fun create(): DataSource<Int, TopItem> {
        val filmDataSource = FilmDataSource(apiService, compositeDisposable)

        filmLiveDataSource.postValue(filmDataSource)
        return filmDataSource
    }
}