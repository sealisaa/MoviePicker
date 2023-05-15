package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.moviepicker.R
import com.example.moviepicker.data.model.Movie
import com.example.moviepicker.adapters.HorizontalMovieCardAdapter
import com.example.moviepicker.databinding.FragmentSearchResultBinding


class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private val adapter = HorizontalMovieCardAdapter()
    private val imageIdList = listOf(
        R.drawable.the_grand_budapest_hotel
    )
    private var index = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater)
        _binding?.apply {
    //            moviesRecyclerView.layoutManager = LinearLayoutManager(this@SearchResultFragment.context)
            moviesRecyclerView.layoutManager = GridLayoutManager(activity , 1)
            moviesRecyclerView.adapter = adapter
            for (i in 1..6) {
                val movie = Movie(imageIdList[index], "Отель «Гранд Будапешт»", "Уэс Андерсон")
                adapter.addItem(movie)
            }
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }
}