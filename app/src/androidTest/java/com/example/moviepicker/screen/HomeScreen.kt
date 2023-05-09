package com.example.moviepicker.screen

import com.example.moviepicker.ui.MainActivity
import com.kaspersky.kaspresso.screens.KScreen
import com.example.moviepicker.R
import io.github.kakaocup.kakao.text.KButton


object HomeScreen : KScreen<HomeScreen>() {
    override val layoutId: Int = R.layout.activity_main
    override val viewClass: Class<*> = MainActivity::class.java

    val profileFragmentButton = KButton { withId(R.id.profileFragment) }
    val searchFragmentButton = KButton { withId(R.id.searchFragment) }

}