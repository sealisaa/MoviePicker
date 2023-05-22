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
import api.service.KPApiService
import com.bumptech.glide.Glide
import com.example.moviepicker.R
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.api.FilmDetailsRepository
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.databinding.FragmentDescriptionBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel
import com.example.moviepicker.ui.viewmodel.MovieViewModel
import io.paperdb.Paper


class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MovieViewModel
    private lateinit var movieRepository: FilmDetailsRepository

    private val favouritesViewModel: FavouritesViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater)

        val movieId = arguments?.getInt("movieId") ?: -1
        Log.d("DescriptionFragment", movieId.toString())


        with(binding) {
            binding.backButton.setOnClickListener {
                findNavController().popBackStack()
            }
            saveButton.setOnClickListener {
                if (favouritesViewModel.savedMoviesId.contains(movieId)) {
                    favouritesViewModel.deleteMovie(movieId)
                    saveButton.setImageResource(R.drawable.ic_save_button)
                } else {
                    favouritesViewModel.saveMovie(movieId)
                    saveButton.setImageResource(R.drawable.ic_unsave_button)
                }
                try {
                    Paper.book().write("favouriteMovies", favouritesViewModel.savedMoviesId)
                } catch (e: Exception) {
                    Log.e("DescriptionFragment", e.stackTraceToString())
                }
            }
            Log.d("DescriptionFragment", favouritesViewModel.savedMoviesId.toString())
        }

        val apiService: KPApiService = DBClient.getClient()
        movieRepository = FilmDetailsRepository(apiService)

        viewModel = getViewModel(movieId)
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            with(binding) {

                movieName.text = it.nameRu
                textViewDescription.text = it.description
                textViewCountry.text = it.countries[0].country
                textViewGenre.text = it.genres[0].genre
//                if (it.rating?.rating != null) {
//                    textViewRating.text = it.rating?.rating.toString()
//                    ratingImage.visibility = View.VISIBLE
//                } else {
//                    ratingField.visibility = View.GONE
//                }

                val kinopoiskId = it.kinopoiskId

                if (favouritesViewModel.savedMoviesId.contains(kinopoiskId)) {
                    saveButton.setImageResource(R.drawable.ic_unsave_button)
                }
            }
            Glide.with(this).load(it.posterUrl).into(binding.poster)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            with(binding) {
//                ratingField.visibility = if (it == NetworkState.LOADED) View.VISIBLE else View.GONE
                saveButton.visibility = if (it == NetworkState.LOADED) View.VISIBLE else View.GONE
                linkButton.visibility = if (it == NetworkState.LOADED) View.VISIBLE else View.GONE
                progressBar.visibility = if (it == NetworkState.LOADING) View.VISIBLE else View.GONE
                textViewConnection.visibility = if (it == NetworkState.ERROR) View.VISIBLE else View.GONE
            }
        }


        return binding.root
    }

    private fun getViewModel(movieId: Int): MovieViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MovieViewModel(movieRepository, movieId) as T
            }
        })[MovieViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}