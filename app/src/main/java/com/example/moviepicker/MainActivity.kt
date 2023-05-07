package com.example.moviepicker

import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.ThreadPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import api.model.movie.Film
import api.service.DBClient
import api.service.FilmDetailsRepository
import api.service.FilmViewModel
import api.service.KPApiService
import com.example.moviepicker.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.paperdb.Paper


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    //    val kinopoiskApiService = KPApiService("468bb0ae-2abc-455d-af87-155b35af4053")
    private lateinit var viewModel: FilmViewModel
    private lateinit var filmRepository: FilmDetailsRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bottomNav: BottomNavigationView = binding.bottomNavigationView
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController: NavController = navHostFragment.navController
        bottomNav.setupWithNavController(navController)


        val filmId = 301
        val apiService: KPApiService = DBClient.getClient()
        filmRepository = FilmDetailsRepository(apiService)

        viewModel = getViewModel(filmId)


    }

    fun bindUI(it: Film) {

    }

    private fun initialize() {
        Paper.init(this)
    }

    private fun getViewModel(filmId: Int): FilmViewModel {
        return ViewModelProvider(this, object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return FilmViewModel(filmRepository, filmId) as T
            }
        })[FilmViewModel::class.java]
    }

}