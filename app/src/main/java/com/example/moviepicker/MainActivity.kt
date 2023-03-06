package com.example.moviepicker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import api.service.KinopoiskApiService
import api.model.Result

class MainActivity : AppCompatActivity() {
    val kinopoiskApiService = KinopoiskApiService("api token")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val result = kinopoiskApiService.getFilm(301)
        when (result) {
            is Result.Success -> result.getOrNull().data.description?.let { Log.d("My_tag", it) } /*actually null is possible only for Failure*/
            is Result.Failure -> Log.d("My_tag","NULL:(")/*handle somehow*/
        }
    }
}