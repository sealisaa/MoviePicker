package com.example.moviepicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import api.service.KinopoiskApiService
import com.example.moviepicker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    val kinopoiskApiService = KinopoiskApiService("468bb0ae-2abc-455d-af87-155b35af4053")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigationView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController

        bottomNav.setupWithNavController(navController)

    }

}