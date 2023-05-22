package com.example.moviepicker.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import api.service.*
import com.example.moviepicker.MoviesByFiltersPagedListRepository
import com.example.moviepicker.adapters.MoviesByGenrePagedListAdapter
import com.example.moviepicker.ui.viewmodel.MoviesByGenreViewModel
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.databinding.FragmentGenreMoviesBinding

class GenreMoviesFragment : Fragment() {
    private var _binding: FragmentGenreMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesByGenreViewModel

    lateinit var movieRepository: MoviesByFiltersPagedListRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreMoviesBinding.inflate(inflater)
        val apiService: KPApiService = DBClient.getClient()
        val genreTitle = arguments?.getString("genreTitle") ?: ""
        binding.genreTitle.text = genreTitle
        movieRepository = MoviesByFiltersPagedListRepository(apiService)
        viewModel = getViewModel(getGenreByTitle(genreTitle))
        val movieAdapter = MoviesByGenrePagedListAdapter(this)

        var gridLayoutManager = GridLayoutManager(this.context, 3)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = GridLayoutManager(this.context, 5)
        }

        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.DATA_VIEW_TYPE) return 1    // DATA_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                            // FOOTER_VIEW_TYPE will occupy all 3 span
            }
        }
        with(binding) {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            genreMoviesRecyclerView.layoutManager = gridLayoutManager
            genreMoviesRecyclerView.setHasFixedSize(true)
            genreMoviesRecyclerView.adapter = movieAdapter
        }

        viewModel.moviePagedList.observe(viewLifecycleOwner) {
            movieAdapter.submitList(it)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textViewConnectionGenreMovies.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                movieAdapter.setNetworkState(it)
            }
        }

        return binding.root

//
//        val view = inflater.inflate(R.layout.fragment_genre_movies, container, false)
//        val backButton : AppCompatImageButton = view.findViewById(R.id.backButton)
//        backButton.setOnClickListener {
//            findNavController().popBackStack()
//        }
//        val recyclerView = view.findViewById<RecyclerView>(R.id.genreMoviesRecyclerView)
//        recyclerView.layoutManager = GridLayoutManager(activity, 3)
//        recyclerView.adapter = VerticalMovieCardAdapter(this)
//
//        return view
    }

    private fun getViewModel(genreId: Int?): MoviesByGenreViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MoviesByGenreViewModel(movieRepository, genreId) as T
            }
        })[MoviesByGenreViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getGenreByTitle(title: String): Int? {
        return when (title) {
            "Thrillers", "Триллеры" -> 1
            "Comedies", "Комедии" -> 13
            "Cartoons", "Мультфильмы" -> 18
            "Horrors", "Ужасы" -> 17
            else -> null
        }
    }
}