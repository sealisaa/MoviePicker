package com.example.moviepicker

import api.model.Result
import api.model.top.movie.TopType
import api.service.KinopoiskApiService
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test

//@Ignore
class KinopoiskApiServiceTest {

    private val kinopoiskApiService: KinopoiskApiService = KinopoiskApiService("4522d8bc-6718-4f11-b449-7b37cdff4577")

    @Test
    fun `test getFilmInfo will return valid film when id exists`() {
        val result = kinopoiskApiService.getFilm(301)

        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getFrames will return valid film when id exists`() {
        val result = kinopoiskApiService.getFrames(301)

        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getVideos will return valid film when id exists`() {
        val result = kinopoiskApiService.getVideos(301)

        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getStudios will return valid film when id exists`() {
        val result = kinopoiskApiService.getStudios(301)

        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getSequelsAndPrequels will return valid film when id exists`() {
        val result = kinopoiskApiService.getSequelsAndPrequels(301)

        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test searchByKeyword will return valid keyword`() {
        val result = kinopoiskApiService.searchByKeyword("avengers")
        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getTop will return valid top 250`() {
        val result = kinopoiskApiService.getTop(TopType.TOP_250_BEST_FILMS)
        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getStaff will return valid staff`() {
        val result = kinopoiskApiService.getStaff(301)
        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }

    @Test
    fun `test getPerson will return valid person`() {
        val result = kinopoiskApiService.getPerson(301)
        assertTrue(result is Result.Success)
        assertNotNull((result as Result.Success).result)
    }
}
