package com.example.moviepicker.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.navigation.fragment.findNavController
import com.example.moviepicker.R
import com.example.moviepicker.databinding.FragmentSearchBinding


class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editTextSearch.setOnEditorActionListener { textView, i, keyEvent ->
            return@setOnEditorActionListener when (i) {
                EditorInfo.IME_ACTION_SEND -> {
                    val bundle = Bundle()
                    bundle.putString("keyword", textView.text.toString())
                    findNavController().navigate(R.id.action_searchFragment_to_searchResultFragment, bundle)
                    true
                }
                else -> false
            }
        }
        binding.searchBackground.setOnClickListener {
            findNavController().navigate(R.id.action_searchFragment_to_filtersFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}