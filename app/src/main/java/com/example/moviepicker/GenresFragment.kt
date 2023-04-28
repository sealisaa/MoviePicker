package com.example.moviepicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepicker.adapters.GenresAdapter


/**
 * A simple [Fragment] subclass.
 * Use the [GenresFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenresFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_genres, container, false)
        val recyclerView = view.findViewById<RecyclerView>(R.id.genresRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = GenresAdapter(this)
        return view
    }
}