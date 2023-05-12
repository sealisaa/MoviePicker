package api.service.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import api.model.top.movie.TopItem
import api.service.KPApiService
import io.reactivex.disposables.CompositeDisposable

class PopularFilmsDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, TopItem>() {

    val filmLiveDataSource = MutableLiveData<PopularFilmDataSource>()

    override fun create(): DataSource<Int, TopItem> {
        val popularFilmDataSource = PopularFilmDataSource(apiService, compositeDisposable)

        filmLiveDataSource.postValue(popularFilmDataSource)
        return popularFilmDataSource
    }
}