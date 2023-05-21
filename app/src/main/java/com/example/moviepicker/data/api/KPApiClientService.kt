package com.example.moviepicker.data.api

import android.util.Log
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.github.kittinunf.fuel.httpGet

internal class KPApiClientService(private val token: String, private val timeout: Int) {
    private val mapper: ObjectMapper = jacksonObjectMapper().registerModule(JavaTimeModule())

    companion object {
        const val AUTH_HEADER = "X-API-KEY"
        const val MAIN_API_URL_V2_2 = "https://kinopoiskapiunofficial.tech/api/v2.2"

        const val GET_FILM = "/films"
        const val GET_TOP = "/top"
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
                Log.d("HTTP-status: ", response.responseMessage)
                Log.e("ERROR: ", response.statusCode.toString())
                return null
            }

            is com.github.kittinunf.result.Result.Success -> {
                Log.d("HTTP-status: ", response.statusCode.toString())
                Log.e("KPApiClientService", result.get())
                return mapper.readValue(result.get(), clazz)
            }
        }
    }

}