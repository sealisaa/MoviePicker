package com.example.moviepicker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import api.service.DBClient
import api.service.FilmDetailsRepository
import api.service.FilmViewModel
import api.service.KPApiService
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_description, container, false)
        val backButton : AppCompatImageButton = view.findViewById(R.id.backButton)
        backButton.setOnClickListener {
            findNavController().popBackStack()
        }

        viewModel.movieDetails.observe(viewLifecycleOwner) {
            with(binding) {
                movieName.text = it.data.nameRu
                textViewDescription.text = it.data.description
            }
            Glide.with(this).load(it.data.posterUrl).into(binding.poster)
        }
        return view
    }



}