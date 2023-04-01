package com.example.moviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView


class PopularMoviesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_popular_movies, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.popularMoviesRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(activity, 3)
        recyclerView.adapter = PopularMoviesAdapter()
        return view
    }
}