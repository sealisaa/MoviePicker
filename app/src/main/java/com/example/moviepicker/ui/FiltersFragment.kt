package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviepicker.R
import com.example.moviepicker.databinding.FragmentFiltersBinding


/**
 * A simple [Fragment] subclass.
 * Use the [FiltersFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FiltersFragment : Fragment() {

    private var _binding: FragmentFiltersBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFiltersBinding.inflate(inflater, container, false)
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