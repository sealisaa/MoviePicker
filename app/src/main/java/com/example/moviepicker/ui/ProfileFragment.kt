package com.example.moviepicker.ui

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import api.service.KPApiService
import com.example.moviepicker.R
import com.example.moviepicker.adapters.FavouritesAdapter
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.repository.FavouriteMoviesRepository
import com.example.moviepicker.databinding.FragmentProfileBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel
import java.lang.StringBuilder

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var movieRepository: FavouriteMoviesRepository
    private lateinit var adapter: FavouritesAdapter

    private val favouriteMoviesList: MutableList<Film> = mutableListOf()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)

        adapter = FavouritesAdapter(this, favouriteMoviesList)
        val linearLayoutManager = LinearLayoutManager(this.context)

        with(binding) {
            favouritesRecyclerView.layoutManager = linearLayoutManager
            favouritesRecyclerView.setHasFixedSize(true)
            favouritesRecyclerView.adapter = adapter
        }

        val apiService: KPApiService = DBClient.getClient()
        movieRepository = FavouriteMoviesRepository(apiService)

        viewModel = ViewModelProvider(requireActivity())[FavouritesViewModel::class.java]
        viewModel.favouriteMovies.observe(viewLifecycleOwner) {
            Log.d("ProfileFragment", it.size.toString())
            adapter.updateList(it)
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }

        binding.textViewStatistics.text = setStatistics()
    }

    override fun onResume() {
        super.onResume()
        if (viewModel.lastItemDeleted != -1) {
            adapter.notifyRemoved(viewModel.lastItemDeleted)
            viewModel.lastItemDeleted = -1
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setStatistics() : String {
        val exceptions = arrayListOf(11, 12, 13, 14)
        val textMovies =
            when (viewModel.savedMoviesId.size % 10) {
                1 -> "Добавлен "
                else -> "Добавлено "
            }
        val textAdded =
            if (viewModel.savedMoviesId.size % 100 in exceptions) {
                " фильмов"
            } else {
                when (viewModel.savedMoviesId.size % 10) {
                    1 -> " фильм"
                    2, 3, 4 -> " фильма"
                    else -> " фильмов"
                }
            }
        return StringBuilder().append(textMovies)
            .append(viewModel.savedMoviesId.size)
            .append(textAdded).toString()
    }

}