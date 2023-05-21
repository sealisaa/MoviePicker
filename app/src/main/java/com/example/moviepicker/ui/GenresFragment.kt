package com.example.moviepicker.ui

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.moviepicker.R
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
        var layoutManager = LinearLayoutManager(activity);
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            layoutManager = GridLayoutManager(this.context, 2)
        }
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = GenresAdapter(
            this,
            mutableListOf(
                resources.getString(R.string.cartoon),
                resources.getString(R.string.thriller),
                resources.getString(R.string.comedy),
                resources.getString(R.string.horror)
            ),
            mutableListOf(
                R.drawable.cartoon,
                R.drawable.thriller,
                R.drawable.comedy,
                R.drawable.horror
            )
        )
        return view
    }
}