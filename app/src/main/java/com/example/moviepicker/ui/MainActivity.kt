package com.example.moviepicker.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.moviepicker.R
import com.example.moviepicker.databinding.ActivityMainBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.paperdb.Paper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        initialize()
    }

    private fun initialize() {
        Paper.init(this)
    }

}