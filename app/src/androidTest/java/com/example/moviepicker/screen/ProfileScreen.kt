package com.example.moviepicker.screen

import com.example.moviepicker.ui.ProfileFragment
import com.example.moviepicker.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.recycler.KRecyclerView
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object ProfileScreen : KScreen<ProfileScreen>() {

    override val layoutId: Int = R.layout.fragment_profile
    override val viewClass: Class<*> = ProfileFragment::class.java


    val settingsButton = KButton { withId(R.id.settingsButton) }
    val textViewTitle = KTextView { withId(R.id.textViewResults) }
    val textViewStatistics = KTextView { withId(R.id.textViewStatistics) }
    val textViewAdded = KTextView { withId(R.id.textViewAdded) }
    val recycler = KRecyclerView(builder = { withId(R.id.moviesRecyclerView) }, {})

    fun simpleClick(button: KButton) {
        button {
            isVisible()
            click()
        }
    }
}