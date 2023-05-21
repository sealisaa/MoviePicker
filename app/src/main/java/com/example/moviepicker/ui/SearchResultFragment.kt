package com.example.moviepicker.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import api.service.KPApiService
import com.example.moviepicker.MoviesByFiltersPagedListRepository
import com.example.moviepicker.adapters.MoviesByFiltersAdapter
import com.example.moviepicker.data.MoviesByGenreViewModel
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.databinding.FragmentSearchResultBinding


class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesByGenreViewModel

    lateinit var movieRepository: MoviesByFiltersPagedListRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchResultBinding.inflate(inflater)
        val apiService: KPApiService = DBClient.getClient()
        movieRepository = MoviesByFiltersPagedListRepository(apiService)
        val keyword = arguments?.getString("keyword")
        val rating = arguments?.getInt("rating")
        val yearFrom = arguments?.getInt("yearFrom")
        val yearTo = arguments?.getInt("yearTo")
        val countryName = arguments?.getString("countryName")
        val genreName = arguments?.getString("genreName")

        viewModel =
            getViewModel(
                keyword = keyword,
                rating = rating,
                yearFrom = yearFrom,
                yearTo = yearTo,
                countryId = getCountryIdByTitle(countryName),
                genreId = getGenreIdByTitle(genreName)
            )
        Log.e("data", "keyword " + (keyword ?: "NULL"))
        Log.e("data", "rating " + rating.toString())
        Log.e("data", "yearFrom " + yearFrom.toString())
        Log.e("data", "yearTo " + yearTo.toString())
        Log.e("data", "countryId " + getCountryIdByTitle(countryName).toString())
        Log.e("data", "genreId " + getGenreIdByTitle(genreName).toString())

        val adapter = MoviesByFiltersAdapter(this)
        val gridLayoutManager = GridLayoutManager(this.context, 1)

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                if (viewType == adapter.DATA_VIEW_TYPE) return 1    // DATA_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                            // FOOTER_VIEW_TYPE will occupy all 3 span
            }

        }
        binding.apply {
            moviesRecyclerView.layoutManager = gridLayoutManager
            moviesRecyclerView.setHasFixedSize(true)
            moviesRecyclerView.adapter = adapter
        }

        viewModel.moviePagedList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textViewConnection.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        }
        return binding.root
    }

    private fun getViewModel(
        countryId: Int? = null,
        genreId: Int? = null,
        rating: Int? = null,
        yearFrom: Int? = null,
        yearTo: Int? = null,
        keyword: String? = null
    ): MoviesByGenreViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MoviesByGenreViewModel(
                    movieRepository, keyword = keyword,
                    rating = rating,
                    yearFrom = yearFrom,
                    yearTo = yearTo,
                    countryId = countryId,
                    genreId = genreId
                ) as T
            }
        })[MoviesByGenreViewModel::class.java]
    }

    private fun getGenreIdByTitle(title: String?): Int? {
        return when (title) {
            "Thrillers", "Триллеры" -> 1
            "Comedies", "Комедии" -> 13
            "Cartoons", "Мультфильмы" -> 18
            "Horrors", "Ужасы" -> 17
            else -> null
        }
    }

    private fun getCountryIdByTitle(title: String?): Int? {
        return when (title) {
            "Russia", "Россия" -> 34
            "USSR", "СССР" -> 33
            "USA", "США" -> 1
            "Italy", "Италия" -> 10
            "France", "Франция" -> 3
            "Germany", "Германия" -> 9
            else -> null
        }
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