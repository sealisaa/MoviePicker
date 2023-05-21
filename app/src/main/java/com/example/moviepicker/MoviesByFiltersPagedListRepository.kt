package com.example.moviepicker

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import api.service.*
import com.example.moviepicker.data.repository.MovieByFiltersDataSource
import com.example.moviepicker.data.repository.MoviesByFiltersDataSourceFactory
import com.example.moviepicker.data.model.movie.FilmItem
import com.example.moviepicker.data.repository.NetworkState
import io.reactivex.disposables.CompositeDisposable

class MoviesByFiltersPagedListRepository(private val apiService: KPApiService) {

    lateinit var filmPagedList: LiveData<PagedList<FilmItem>>
    lateinit var popularFilmsDataSourceFactory: MoviesByFiltersDataSourceFactory

    fun fetchLiveMoviePagedList(
        compositeDisposable: CompositeDisposable, countryId: Int? = null,
        genreId: Int? = null,
        rating: Int? = null,
        yearFrom: Int? = null,
        yearTo: Int? = null,
        keyword: String? = null): LiveData<PagedList<FilmItem>> {
        popularFilmsDataSourceFactory = MoviesByFiltersDataSourceFactory(apiService, compositeDisposable
                , keyword = keyword,
            rating = rating,
            yearFrom = yearFrom,
            yearTo = yearTo,
            countryId = countryId,
            genreId = genreId)

        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setPageSize(POST_PER_PAGE)
            .build()

        filmPagedList = LivePagedListBuilder(popularFilmsDataSourceFactory, config).build()
        return filmPagedList
    }

    fun getNetworkState(): LiveData<NetworkState> {
        return Transformations.switchMap<MovieByFiltersDataSource, NetworkState>(
            popularFilmsDataSourceFactory.filmLiveDataSource, MovieByFiltersDataSource::networkState
        )
    }
}