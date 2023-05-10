package com.example.moviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import api.service.*
import api.service.repository.NetworkState
import com.example.moviepicker.adapters.MoviesByGenrePagedListAdapter
import com.example.moviepicker.databinding.FragmentGenreMoviesBinding

class GenreMoviesFragment : Fragment() {
    private var _binding: FragmentGenreMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MoviesByGenreViewModel

    lateinit var movieRepository: MoviePagedListRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGenreMoviesBinding.inflate(inflater)
        val apiService: KPApiService = DBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()
        val movieAdapter = MoviesByGenrePagedListAdapter(this)
        val gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.DATA_VIEW_TYPE) return 1    // DATA_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                            // FOOTER_VIEW_TYPE will occupy all 3 span
            }
        }
        with (binding) {
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
            binding.textViewConnection.visibility =
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

    private fun getViewModel(): MoviesByGenreViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MoviesByGenreViewModel(movieRepository) as T
            }
        })[MoviesByGenreViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}