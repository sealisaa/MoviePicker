package com.example.moviepicker.data.api

import com.example.moviepicker.data.model.movie.AppendType
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.model.top.movie.TopResult
import com.example.moviepicker.data.model.top.movie.TopType
import com.example.moviepicker.data.model.movie.FilmsByFilters
import com.example.moviepicker.data.api.KPApiClientService.Companion.GET_FILM
import com.example.moviepicker.data.api.KPApiClientService.Companion.GET_TOP
import com.example.moviepicker.data.api.KPApiClientService.Companion.MAIN_API_URL_V2_1
import com.example.moviepicker.data.api.KPApiClientService.Companion.MAIN_API_URL_V2_2
import io.reactivex.Single

const val FIRST_PAGE = 1
const val POST_PER_PAGE = 20

class KPApiService(token: String, timeoutMs: Int = 15000) {
    private val kpApiClientService: com.example.moviepicker.data.api.KPApiClientService =
        com.example.moviepicker.data.api.KPApiClientService(token, timeoutMs)

    /**
     * This method retrieves film data.
     *
     * @param kinopoiskId id of film from kinopoisk.
     * @param appendTypes to add additional info to response. See [AppendType].
     */
//    fun getFilm(kinopoiskId: Int, appendTypes: Iterable<AppendType> = emptyList()): Result<Film> {
//        require(kinopoiskId > 0) { "Film id should be more than 0" }
//        val appends = appendTypes.joinToString()
//        return kpApiClientService.requestFilm(
//            MAIN_API_URL_V2_1,
//            "$GET_FILM/$kinopoiskId?append_to_response=$appends",
//            Film::class.java
//        )
//    }
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

    /**
     * Returns top by particular top type [TopType].
     *
     * @param topType see [TopType].
     * @param page page.
     */
    fun getTop(topType: TopType, page: Int = 1): Single<TopResult> {
        return Single.fromCallable{
            kpApiClientService.request(
                MAIN_API_URL_V2_2,
                "$GET_FILM$GET_TOP?type=$topType&page=$page",
                TopResult::class.java
            )
        }
    }

//    /**
//     * Returns frames for particular kinopoiskId.
//     *
//     * @param kinopoiskId id of film from kinopoisk.
//     */
//    fun getFrames(kinopoiskId: Int): Result<GalleryResult> {
//        require(kinopoiskId > 0) { "Film id should be more than 0" }
//        return kpApiClientService.request(
//            MAIN_API_URL_V2_1,
//            "$GET_FILM/$kinopoiskId$GET_FRAMES",
//            GalleryResult::class.java
//        )
//    }

//    /**
//     * Returns videos for particular kinopoiskId.
//     *
//     * @param kinopoiskId id of film from kinopoisk.
//     */
//    fun getVideos(kinopoiskId: Int): Result<VideoResult> {
//        require(kinopoiskId > 0) { "Film id should be more than 0" }
//        return kpApiClientService.request(
//            MAIN_API_URL_V2_1,
//            "$GET_FILM/$kinopoiskId$GET_VIDEOS",
//            VideoResult::class.java
//        )
//    }

//    /**
//     * Returns studios for particular kinopoiskId.
//     *
//     * @param kinopoiskId id of film from kinopoisk.
//     */
//    fun getStudios(kinopoiskId: Int): Result<StudioResult> {
//        require(kinopoiskId > 0) { "Film id should be more than 0" }
//        return kpApiClientService.request(
//            MAIN_API_URL_V2_1,
//            "$GET_FILM/$kinopoiskId$GET_STUDIOS",
//            StudioResult::class.java
//        )
//    }

//    /**
//     * Returns sequels and prequels for particular kinopoiskId.
//     *
//     * @param kinopoiskId id of film from kinopoisk.
//     */
//    fun getSequelsAndPrequels(kinopoiskId: Int): Result<List<RelatedFilmItem>> {
//        require(kinopoiskId > 0) { "Film id should be more than 0" }
//        return kpApiClientService.request(
//            MAIN_API_URL_V2_1,
//            "$GET_FILM/$kinopoiskId$GET_SEQUELS_AND_PREQUELS",
//            List::class.java as Class<List<RelatedFilmItem>>
//        )
//    }

//    /**
//     * Returns search result by keyword.
//     *
//     * @param keyword keyword to search.
//     * @param page page.
//     */
//    fun searchByKeyword(keyword: String, page: Int = 1): Result<SearchResult> {
//        return kpApiClientService.request(
//            MAIN_API_URL_V2_1,
//            "$GET_FILM$SEARCH_BY_KEYWORD?keyword=$keyword&page=$page",
//            SearchResult::class.java
//        )
//    }



//    /**
//     * Returns all persons by particular film id.
//     *
//     * @param kinopoiskFilmId film id.
//     */
//    fun getStaff(kinopoiskFilmId: Int): Result<List<StaffItem>> {
//        return kpApiClientService.request(
//            MAIN_API_URL_V1,
//            "$GET_STAFF?filmId=$kinopoiskFilmId",
//            List::class.java as Class<List<StaffItem>>
//        )
//    }

//    /**
//     * Returns person by particular film..
//     *
//     * @param kinopoiskId person id.
//     */
//    fun getPerson(kinopoiskId: Int): Result<Person> {
//        return kpApiClientService.request(
//            MAIN_API_URL_V1,
//            "$GET_STAFF/$kinopoiskId",
//            Person::class.java
//        )
//    }

    fun getFilmsByFileters(
        countryId: Int? = null,
        genreId: Int? = null,
        ratingFrom: Int? = null,
        ratingTo: Int? = null,
        yearFrom: Int? = null,
        yearTo: Int? = null,
        keyword: String? = null,
        page: Int? = null
    ): Result<FilmsByFilters>? {
//        return kpApiClientService.request(
//            MAIN_API_URL_V2_2,
//            ("$GET_FILM?${if (countryId != null) "countries=${countryId}&" else ""}${if (countryId != null) "genres=${genreId}&" else ""}order=RATING&type=FILM&" +
//                    "${if (ratingFrom != null) "ratingFrom=${ratingFrom}&" else ""}${if (ratingTo != null) "ratingTo=${ratingTo}&" else ""}" +
//                    "${if (yearFrom != null) "yearFrom=${yearFrom}&" else ""}${if (yearTo != null) "yearTo=${yearTo}&" else ""}" +
//                    "${if (keyword != null) "keyword=${keyword}&" else ""}${if (page != null) "page=${page}&" else ""}").dropLast(1),
//           FilmsByFilters::class.java
//        )
        return null
    }


}