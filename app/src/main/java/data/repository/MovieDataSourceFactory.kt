package data.repository

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import data.model.top.movie.TopItem
import data.api.KPApiService
import io.reactivex.disposables.CompositeDisposable

class MovieDataSourceFactory(
    private val apiService: KPApiService,
    private val compositeDisposable: CompositeDisposable
) : DataSource.Factory<Int, TopItem>() {

    val filmLiveDataSource = MutableLiveData<MovieDataSource>()

    override fun create(): DataSource<Int, TopItem> {
        val movieDataSource = MovieDataSource(apiService, compositeDisposable)

        filmLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}