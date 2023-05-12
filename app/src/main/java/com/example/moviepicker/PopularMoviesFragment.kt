package com.example.moviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import api.service.DBClient
import api.service.KPApiService
import api.service.PopularFilmsViewModel
import api.service.repository.NetworkState
import com.example.moviepicker.adapters.PopularMoviesPagedListAdapter
import com.example.moviepicker.databinding.FragmentPopularMoviesBinding

class PopularMoviesFragment : Fragment() {

    private var _binding: FragmentPopularMoviesBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: PopularFilmsViewModel

    lateinit var movieRepository: PopularMoviePagedListRepository

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPopularMoviesBinding.inflate(inflater)

//        val recyclerView = view.findViewById<RecyclerView>(R.id.popularMoviesRecyclerView)
//        recyclerView.layoutManager = GridLayoutManager(activity, 3)
//        recyclerView.adapter = PopularMoviesAdapter(this)
        val apiService: KPApiService = DBClient.getClient()
        movieRepository = PopularMoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = PopularMoviesPagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.DATA_VIEW_TYPE) return 1    // DATA_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                            // FOOTER_VIEW_TYPE will occupy all 3 span
            }
        };

        with (binding) {
            popularMoviesRecyclerView.layoutManager = gridLayoutManager
            popularMoviesRecyclerView.setHasFixedSize(true)
            popularMoviesRecyclerView.adapter = movieAdapter
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

    private fun getViewModel(): PopularFilmsViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PopularFilmsViewModel(movieRepository) as T
            }
        })[PopularFilmsViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}