package com.example.moviepicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import data.api.DBClient
import data.api.FilmDetailsRepository
import com.example.moviepicker.ui.viewmodel.MovieViewModel
import data.api.KPApiService
import data.repository.NetworkState
import com.bumptech.glide.Glide
import com.example.moviepicker.databinding.FragmentDescriptionBinding


/**
 * A simple [Fragment] subclass.
 * Use the [DescriptionFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DescriptionFragment : Fragment() {

    private var _binding: FragmentDescriptionBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : MovieViewModel
    private lateinit var filmRepository: FilmDetailsRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        val apiService: KPApiService = DBClient.getClient()
        filmRepository = FilmDetailsRepository(apiService)

        viewModel = getViewModel(arguments?.getInt("movieId") ?: -1)
        viewModel.movieDetails.observe(viewLifecycleOwner) {
            with(binding) {
                it.data.kinopoiskId
                movieName.text = it.data.nameRu
                textViewDescription.text = it.data.description
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
                return MovieViewModel(filmRepository, filmId) as T
            }
        })[MovieViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}