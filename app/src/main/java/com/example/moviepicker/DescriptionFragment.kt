package com.example.moviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import api.service.FilmDetailsRepository
import api.service.FilmViewModel
import api.service.repository.NetworkState
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

    private val viewModel : FilmViewModel by activityViewModels()
    private lateinit var filmRepository: FilmDetailsRepository


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDescriptionBinding.inflate(inflater)

        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}