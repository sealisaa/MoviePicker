package com.example.moviepicker.ui

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import api.service.KPApiService
import com.example.moviepicker.adapters.MoviesBySearchAdapter
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.repository.NetworkState
import com.example.moviepicker.data.repository.SearchPagedListRepository
import com.example.moviepicker.databinding.FragmentSearchResultBinding
import com.example.moviepicker.ui.viewmodel.SearchResultViewModel

class SearchResultFragment : Fragment() {

    private var _binding: FragmentSearchResultBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: SearchResultViewModel

    lateinit var movieRepository: SearchPagedListRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        _binding = FragmentSearchResultBinding.inflate(inflater)
        val apiService: KPApiService = DBClient.getClient()
        movieRepository = SearchPagedListRepository(apiService)

        val keyword = arguments?.getString("keyword") ?: ""

        viewModel = getViewModel(keyword)

        val adapter = MoviesBySearchAdapter(this)
        var gridLayoutManager = GridLayoutManager(this.context, 1)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            gridLayoutManager = GridLayoutManager(this.context, 1)
        }
        gridLayoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {
                val viewType = adapter.getItemViewType(position)
                if (viewType == adapter.DATA_VIEW_TYPE) return 1    // DATA_VIEW_TYPE will occupy 1 out of 3 span
                else return 3                                            // FOOTER_VIEW_TYPE will occupy all 3 span
            }
        }
        binding.apply {
            moviesRecyclerView.layoutManager = gridLayoutManager
            moviesRecyclerView.setHasFixedSize(true)
            moviesRecyclerView.adapter = adapter
        }

        viewModel.moviePagedList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.networkState.observe(viewLifecycleOwner) {
            binding.progressBar.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.LOADING) View.VISIBLE else View.GONE
            binding.textViewConnection.visibility =
                if (viewModel.listIsEmpty() && it == NetworkState.ERROR) View.VISIBLE else View.GONE
            if (!viewModel.listIsEmpty()) {
                adapter.setNetworkState(it)
            }
        }
        return binding.root
    }

    private fun getViewModel(keyword: String): SearchResultViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchResultViewModel(movieRepository, keyword) as T
            }
        })[SearchResultViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}