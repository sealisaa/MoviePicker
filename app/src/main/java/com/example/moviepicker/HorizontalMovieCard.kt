package com.example.moviepicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class HorizontalMovieCard : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.horizontal_movie_card, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() = HorizontalMovieCard()
    }
}