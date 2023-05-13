package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.moviepicker.R


class HorizontalMovieCard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.horizontal_movie_card, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HorizontalMovieCard()
    }
}