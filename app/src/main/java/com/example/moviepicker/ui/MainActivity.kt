package com.example.moviepicker.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import api.model.favouriteMoviesId
import com.example.moviepicker.R
import com.example.moviepicker.data.api.DBClient
import com.example.moviepicker.data.api.KPApiService
import com.example.moviepicker.data.repository.FavouriteMoviesRepository
import com.example.moviepicker.databinding.ActivityMainBinding
import com.example.moviepicker.ui.viewmodel.FavouritesViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.paperdb.Paper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: FavouritesViewModel
    private lateinit var movieRepository: FavouriteMoviesRepository

    init {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)

        Paper.init(this)
        favouriteMoviesId = (Paper.book().read<ArrayList<Int>>("favouriteMovies") ?: emptyList()) as ArrayList<Int>
        Log.d("MainActivity", favouriteMoviesId.size.toString())

        val apiService: KPApiService = DBClient.getClient()
        movieRepository = FavouriteMoviesRepository(apiService)
        viewModel = getViewModel(favouriteMoviesId, this)

        initialize()
    }

    private fun initialize() {

    }

    private fun getViewModel(savedMoviesId: MutableList<Int>, owner: ViewModelStoreOwner): FavouritesViewModel {
        return ViewModelProvider(owner, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FavouritesViewModel(movieRepository, savedMoviesId) as T
            }
        }).get(FavouritesViewModel::class.java)
    }


}