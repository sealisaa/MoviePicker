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


class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)

        val MPAAs = resources.getStringArray(R.array.MPAAs)
        val arrayAdapterMPAA = ArrayAdapter(requireContext(), R.layout.dropdown_item, MPAAs)
        binding.autoCompleteTextViewMPAA.setAdapter(arrayAdapterMPAA)

        val Countrys = resources.getStringArray(R.array.Countrys)
        val arrayAdapterCountry = ArrayAdapter(requireContext(), R.layout.dropdown_item, Countrys)
        binding.autoCompleteTextViewCountry.setAdapter(arrayAdapterCountry)

        val Genress = resources.getStringArray(R.array.Genress)
        val arrayAdapterGenre = ArrayAdapter(requireContext(), R.layout.dropdown_item, Genress)
        binding.autoCompleteTextViewGenre.setAdapter(arrayAdapterGenre)

        binding.seekBarYear.stepSize = 1.0f

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.applyFiltersButton.setOnClickListener {
            findNavController().navigate(R.id.action_filtersFragment_to_searchResultFragment)
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