package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.example.moviepicker.R
import com.example.moviepicker.databinding.FragmentFiltersBinding
import java.util.logging.Logger
import kotlin.math.roundToInt


class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)

        val Countrys = resources.getStringArray(R.array.Countrys)
        val arrayAdapterCountry = ArrayAdapter(requireContext(), R.layout.dropdown_item, Countrys)
        binding.autoCompleteTextViewCountry.setAdapter(arrayAdapterCountry)

        val Genress = resources.getStringArray(R.array.Genress)
        val arrayAdapterGenre = ArrayAdapter(requireContext(), R.layout.dropdown_item, Genress)
        binding.autoCompleteTextViewGenre.setAdapter(arrayAdapterGenre)


        binding.seekBarYear.setLabelFormatter { value: Float ->
            return@setLabelFormatter "${value.roundToInt()}"
        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.applyFiltersButton.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("keyword", binding.editTextKeyword.text.toString())
            bundle.putInt("rating", binding.seekBarRating.progress)
            bundle.putInt("yearFrom", binding.seekBarYear.valueFrom.toInt())
            bundle.putInt("yearTo", binding.seekBarYear.valueTo.toInt())
            bundle.putString("countryName", binding.autoCompleteCountry.editText?.text.toString())
            bundle.putString("genreName", binding.autoCompleteGenre.editText?.text.toString())
            findNavController().navigate(R.id.action_filtersFragment_to_searchResultFragment, bundle)
        }
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}