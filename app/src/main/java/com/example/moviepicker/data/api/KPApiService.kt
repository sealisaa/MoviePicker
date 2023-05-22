package api.service

import android.util.Log
import com.example.moviepicker.data.api.KPApiClientService
import com.example.moviepicker.data.model.movie.AppendType
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.model.top.movie.TopResult
import com.example.moviepicker.data.model.top.movie.TopType
import com.example.moviepicker.data.model.movie.FilmsByFilters
import com.example.moviepicker.data.api.KPApiClientService.Companion.GET_FILM
import com.example.moviepicker.data.api.KPApiClientService.Companion.GET_TOP
import com.example.moviepicker.data.api.KPApiClientService.Companion.MAIN_API_URL_V2_1
import com.example.moviepicker.data.api.KPApiClientService.Companion.MAIN_API_URL_V2_2
import com.example.moviepicker.data.api.KPApiClientService.Companion.SEARCH_BY_KEYWORD
import com.example.moviepicker.data.model.search.movie.keyword.SearchResult
import io.reactivex.Single

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20

class KPApiService(token: String, timeoutMs: Int = 15000) {
    private val kpApiClientService: KPApiClientService =
        KPApiClientService(token, timeoutMs)

    /**
     * This method retrieves film data.
     *
     * @param kinopoiskId id of film from kinopoisk.
     * @param appendTypes to add additional info to response. See [AppendType].
     */
    fun getFilm(kinopoiskId: Int, appendTypes: Iterable<AppendType> = emptyList()): Single<Film> {
        require(kinopoiskId > 0) { "Film id should be more than 0" }
        return Single.fromCallable {
            val appends = appendTypes.joinToString()
            val result = kpApiClientService.request(
                MAIN_API_URL_V2_1,
                "$GET_FILM/$kinopoiskId?append_to_response=$appends",
                Film::class.java
            )
            result
        }

    }

    fun getFilms(list: List<Int>, appendTypes: Iterable<AppendType> = emptyList()): Single<MutableList<Film>> {
        return Single.fromCallable {
            val appends = appendTypes.joinToString()
            val result = mutableListOf<Film>()
            for (id in list) {
                kpApiClientService.request(
                    MAIN_API_URL_V2_1,
                    "$GET_FILM/$id?append_to_response=$appends",
                    Film::class.java
                )?.let {
                    result.add(
                        it
                    )
                }
            }
            result
        }
    }

    /**
     * Returns top by particular top type [TopType].
     *
     * @param topType see [TopType].
     * @param page page.
     */
    fun getTop(topType: TopType, page: Int = 1): Single<TopResult> {
        return Single.fromCallable {
            kpApiClientService.request(
                MAIN_API_URL_V2_2,
                "$GET_FILM$GET_TOP?type=$topType&page=$page",
                TopResult::class.java
            )
        }
    }

    /**
     * Returns search result by keyword.
     *
     * @param keyword keyword to search.
     * @param page page.
     */
    fun searchByKeyword(keyword: String, page: Int = 1): Single<SearchResult> {
        return Single.fromCallable {
            kpApiClientService.request(
                MAIN_API_URL_V2_1,
                "$GET_FILM$SEARCH_BY_KEYWORD?keyword=$keyword&page=$page",
                SearchResult::class.java
            )
        }
    }


    fun getFilmsByFilters(
        countryId: Int? = null,
        genreId: Int? = null,
        ratingFrom: Int? = null,
        ratingTo: Int? = null,
        yearFrom: Int? = null,
        yearTo: Int? = null,
        keyword: String? = null,
        page: Int? = null
    ): Single<FilmsByFilters> {
        Log.d(
            "request by genre", MAIN_API_URL_V2_2 +
                    ("$GET_FILM?${if (countryId != null) "countries=${countryId}&" else ""}${if (genreId != null) "genres=${genreId}&" else ""}order=RATING&type=FILM&" +
                            "${if (ratingFrom != null) "ratingFrom=${ratingFrom}&" else ""}${if (ratingTo != null) "ratingTo=${ratingTo}&" else ""}" +
                            "${if (yearFrom != null) "yearFrom=${yearFrom}&" else ""}${if (yearTo != null) "yearTo=${yearTo}&" else ""}" +
                            "${if (keyword != null) "keyword=${keyword}&" else ""}${if (page != null) "page=${page}&" else ""}").dropLast(
                        1
                    )
        )
        return Single.fromCallable {
            kpApiClientService.request(
                MAIN_API_URL_V2_2,
                ("$GET_FILM?${if (countryId != null) "countries=${countryId}&" else ""}${if (genreId != null) "genres=${genreId}&" else ""}order=RATING&type=FILM&" +
                        "${if (ratingFrom != null) "ratingFrom=${ratingFrom}&" else ""}${if (ratingTo != null) "ratingTo=${ratingTo}&" else ""}" +
                        "${if (yearFrom != null) "yearFrom=${yearFrom}&" else ""}${if (yearTo != null) "yearTo=${yearTo}&" else ""}" +
                        "${if (keyword != null) "keyword=${keyword}&" else ""}${if (page != null) "page=${page}&" else ""}").dropLast(
                    1
                ),
                FilmsByFilters::class.java
            )
        }
    }


}