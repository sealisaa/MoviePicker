package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import api.model.favouriteMovies
import com.example.moviepicker.R
import com.example.moviepicker.adapters.FavouritesPagedListAdapter
import com.example.moviepicker.data.api.KPApiService
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.databinding.FragmentProfileBinding
import com.example.moviepicker.ui.viewmodel.PopularMoviesViewModel

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PopularMoviesViewModel

    lateinit var movieRepository: MoviePagedListRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProfileBinding.inflate(inflater)

        val apiService: KPApiService = com.example.moviepicker.data.api.DBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)
        viewModel = getViewModel()

        val movieAdapter = FavouritesPagedListAdapter(this)
        val linearLayoutManager = LinearLayoutManager(this.context)

        with (binding) {
            favouritesRecyclerView.layoutManager = linearLayoutManager
            favouritesRecyclerView.setHasFixedSize(true)
            favouritesRecyclerView.adapter = movieAdapter
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
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.settingsButton.setOnClickListener {
            findNavController().navigate(R.id.action_profileFragment_to_settingsFragment)
        }
    }

    private fun getViewModel(): PopularMoviesViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PopularMoviesViewModel(movieRepository) as T
            }
        })[PopularMoviesViewModel::class.java]
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