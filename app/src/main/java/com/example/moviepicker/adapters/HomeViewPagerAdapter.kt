package com.example.moviepicker.adapters

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.moviepicker.ui.GenresFragment
import com.example.moviepicker.ui.PopularMoviesFragment

class HomeViewPagerAdapter(fragment: Fragment, private val listOfTitle: List<String>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = listOfTitle.size

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> return PopularMoviesFragment()
            1 -> return GenresFragment()
            // тут нужен фрагмент с топ-250
            2 -> return PopularMoviesFragment()
        }
        // заменить на какой-нибудь пустой фрагмент
        return PopularMoviesFragment()
    }

}