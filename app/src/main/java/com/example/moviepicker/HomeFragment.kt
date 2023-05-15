package com.example.moviepicker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.example.moviepicker.adapters.HomeViewPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout

    private val listOfTitles = arrayListOf<String>()
    private fun loadTitles() {
        listOfTitles.add(resources.getString(R.string.popular))
        listOfTitles.add(resources.getString(R.string.genresAmount))
        listOfTitles.add(resources.getString(R.string.top))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loadTitles()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_home, container, false)
        viewPager = view.findViewById(R.id.homeViewPager)
        val pagerAdapter = HomeViewPagerAdapter(this, listOfTitles)
        viewPager.adapter = pagerAdapter

        tabLayout = view.findViewById(R.id.homeTabLayout)
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = listOfTitles[position]
        }.attach()

        return view
    }
}