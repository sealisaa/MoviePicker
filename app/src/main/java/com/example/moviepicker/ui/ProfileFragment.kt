package com.example.moviepicker.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import api.model.favouriteMoviesId
import com.example.moviepicker.R
import com.example.moviepicker.adapters.FavouritesAdapter
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.api.KPApiService
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.data.repository.FavouriteMoviesRepository
import com.example.moviepicker.databinding.FragmentProfileBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavouritesViewModel
    private lateinit var movieRepository: FavouriteMoviesRepository
    private lateinit var adapter: FavouritesAdapter
//    private val viewModel : FavouritesViewModel by activityViewModels()

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

//        viewModel = getViewModel(favouriteMoviesId)
        viewModel = ViewModelProvider(requireActivity())[FavouritesViewModel::class.java]
        viewModel.favouriteMovies.observe(viewLifecycleOwner) {
            Log.d("ProfileFragment", it.size.toString())
            adapter.updateList(it)
        }



        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun getViewModel(savedMoviesId: MutableList<Int>): FavouritesViewModel {
//        return ViewModelProvider(requireActivity(), object : ViewModelProvider.Factory {
//            override fun <T : ViewModel> create(modelClass: Class<T>): T {
//                return FavouritesViewModel(movieRepository, savedMoviesId) as T
//            }
//        })[FavouritesViewModel::class.java]
//    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }
}