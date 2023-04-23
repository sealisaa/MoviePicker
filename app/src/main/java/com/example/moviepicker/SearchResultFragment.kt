package com.example.moviepicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import api.model.Movie
import com.example.moviepicker.adapters.HorizontalMovieCardAdapter
import com.example.moviepicker.databinding.FragmentSearchResultBinding


class SearchResultFragment : Fragment() {

    private lateinit var bind: FragmentSearchResultBinding
    private val adapter = HorizontalMovieCardAdapter()
    private val imageIdList = listOf(
        R.drawable.the_grand_budapest_hotel
    )
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bind = FragmentSearchResultBinding.inflate(inflater)
        bind.apply {
//            moviesRecyclerView.layoutManager = LinearLayoutManager(this@SearchResultFragment.context)
            moviesRecyclerView.layoutManager = GridLayoutManager(activity , 1)
            moviesRecyclerView.adapter = adapter
            for (i in 1..6) {
                val movie = Movie(imageIdList[index], "Отель «Гранд Будапешт»", "Уэс Андерсон")
                adapter.addItem(movie)
            }
        }
        return bind.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }
}