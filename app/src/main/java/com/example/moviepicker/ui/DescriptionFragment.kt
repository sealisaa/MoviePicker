package com.example.moviepicker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import api.model.favouriteMoviesId
import com.example.moviepicker.ui.viewmodel.MovieViewModel
import com.example.moviepicker.data.api.KPApiService
import com.example.moviepicker.data.repository.NetworkState
import com.bumptech.glide.Glide
import com.example.moviepicker.R
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.api.FilmDetailsRepository
import com.example.moviepicker.databinding.FragmentDescriptionBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel
import io.paperdb.Paper


class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: FilmDetailsRepository

    private val favouritesViewModel : FavouritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater)

        val movieId = arguments?.getInt("movieId") ?: -1


        with(binding) {
            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            saveButton.setOnClickListener {
                if (favouriteMoviesId.contains(movieId)) {
//                    favouriteMoviesId.remove(movieId)
                    saveButton.setImageResource(R.drawable.ic_save_button)
                } else {
//                    favouriteMoviesId.add(movieId)
                    favouritesViewModel.saveMovie(movieId)
                    saveButton.setImageResource(R.drawable.ic_unsave_button)
                }
                try {
                    Paper.book().write("favouriteMovies", favouriteMoviesId)
                } catch (e : Exception) {
                    Log.e("DescriptionFragment", e.stackTraceToString())
                }
            }
            Log.d("DescriptionFragment", favouriteMoviesId.size.toString())
        }

        val apiService: KPApiService = DBClient.getClient()
        movieRepository = FilmDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            with(binding) {

                movieName.text = it.data.nameRu
                textViewDescription.text = it.data.description
                val kinopoiskId = it.data.kinopoiskId

                if (favouriteMoviesId.contains(kinopoiskId)) {
                    saveButton.setImageResource(R.drawable.ic_unsave_button)
                }
            }
            Glide.with(this).load(it.data.posterUrl).into(binding.poster)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textViewConnection.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
        }


        return binding.root
    }

    private fun getViewModel(filmId: Int): MovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository, filmId) as T
            }
        })[MovieViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}