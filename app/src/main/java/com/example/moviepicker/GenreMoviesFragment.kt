package com.example.moviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepicker.adapters.PopularMoviesAdapter

class GenreMoviesFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_genre_movies, container, false)
        val backButton : AppCompatImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.genreMoviesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = PopularMoviesAdapter(this)

        return view
    }
}