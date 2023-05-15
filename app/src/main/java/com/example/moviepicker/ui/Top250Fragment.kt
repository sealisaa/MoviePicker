package com.example.moviepicker.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import api.service.KPApiService
import com.example.moviepicker.adapters.MoviesPagedListAdapter
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.databinding.FragmentTop250Binding
import com.example.moviepicker.ui.viewmodel.Top250ViewModel

class Top250Fragment : Fragment() {

    private var _binding : FragmentTop250Binding? = null

    private val binding get() = _binding!!

    private lateinit var viewModel: Top250ViewModel

    lateinit var movieRepository: MoviePagedListRepository
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTop250Binding.inflate(inflater)

        val apiService: KPApiService = DBClient.getClient()
        movieRepository = MoviePagedListRepository(apiService)

        viewModel = getViewModel()

        val movieAdapter = MoviesPagedListAdapter(this)

        val gridLayoutManager = GridLayoutManager(this.context, 3)
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = movieAdapter.getItemViewType(position)
                if (viewType == movieAdapter.DATA_VIEW_TYPE) return 1    // DATA_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                            // FOOTER_VIEW_TYPE will occupy all 3 span
            }
        };

        with (binding) {
            top250RecyclerView.layoutManager = gridLayoutManager
            top250RecyclerView.setHasFixedSize(true)
            top250RecyclerView.adapter = movieAdapter
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

    private fun getViewModel(): Top250ViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return Top250ViewModel(movieRepository) as T
            }
        })[Top250ViewModel::class.java]
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}