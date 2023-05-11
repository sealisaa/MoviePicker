package com.example.moviepicker.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class FavouritesViewModel : ViewModel() {
    private val _favouriteMovies = MutableLiveData<List<Int>>()
    val favouriteMovies: LiveData<List<Int>> = _favouriteMovies

    fun updateList(newList: List<Int>) {
        _favouriteMovies.postValue(newList);
    }
}