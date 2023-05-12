package com.example.moviepicker.data.api

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.httpGet

// todo add logs
internal class KPApiClientService(private val token: String, private val timeout: Int) {
    private val mapper: ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    companion object {
        const val AUTH_HEADER = "X-API-KEY"
        const val MAIN_API_URL_V1 = "https://kinopoiskapiunofficial.tech/api/v1"
        const val MAIN_API_URL_V2_1 = "https://kinopoiskapiunofficial.tech/api/v2.1"
        const val MAIN_API_URL_V2_2 = "https://kinopoiskapiunofficial.tech/api/v2.2"

        const val GET_FILM = "/films"
        const val GET_FRAMES = "/frames"
        const val GET_VIDEOS = "/videos"
        const val GET_STUDIOS = "/studios"
        const val GET_SEQUELS_AND_PREQUELS = "/sequels_and_prequels"
        const val GET_TOP = "/top"
        const val GET_STAFF = "/staff"

        const val SEARCH_BY_KEYWORD = "/search-by-keyword"
    }


    fun <T> request(url: String, path: String, clazz: Class<T>): T? {
        val (request, response, result) = (url + path)
            .httpGet()
            .timeout(timeout)
            .timeoutRead(timeout)
            .header(mapOf(AUTH_HEADER to token))
            .responseString()

        when (result) {
            is com.github.kittinunf.result.Result.Failure -> {
                Log.e("HTTP-status: ", response.responseMessage.toString())
                Log.e("ERROR: ", response.statusCode.toString())
                return null
            }

            is com.github.kittinunf.result.Result.Success -> {
                Log.e("HTTP-status: ", response.statusCode.toString())
                return mapper.readValue(result.get(), clazz)
            }
        }
    }

}