package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import api.model.favouriteMoviesId
import com.example.moviepicker.R
import com.example.moviepicker.adapters.FavouritesAdapter
import com.example.moviepicker.data.api.KPApiService
import com.example.moviepicker.data.model.movie.Film
import com.example.moviepicker.databinding.FragmentProfileBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FavouritesViewModel
    lateinit var adapter: FavouritesAdapter
    private val favouriteMoviesList: MutableList<Film> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)

        val favouritesAdapter = FavouritesAdapter(this, favouriteMoviesList)
        val linearLayoutManager = LinearLayoutManager(this.context)

        with (binding) {
            favouritesRecyclerView.layoutManager = linearLayoutManager
            favouritesRecyclerView.setHasFixedSize(true)
            favouritesRecyclerView.adapter = favouritesAdapter
        }

        viewModel = getViewModel()

        viewModel.favouriteMovies.observe(viewLifecycleOwner, Observer { newContent ->
            adapter.updateList(newContent)
        })

        return binding.root
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

    private fun getViewModel(): FavouritesViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FavouritesViewModel() as T
            }
        })[FavouritesViewModel::class.java]
    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchResultFragment()
    }
}