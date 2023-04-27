package com.example.moviepicker.screens

import com.example.moviepicker.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.tabs.KTabLayout

object HomeScreen : KScreen<HomeScreen>() {
    override val layoutId: Int?
        get() = R.layout.fragment_home
    override val viewClass: Class<*>?
        get() = null

    private val tabLayout = KTabLayout { withId(R.id.homeTabLayout) }

    fun goToGenres() {
        this.tabLayout.selectTab(1);
    }
}