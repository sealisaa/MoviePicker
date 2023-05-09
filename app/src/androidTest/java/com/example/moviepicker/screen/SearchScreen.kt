package com.example.moviepicker.screen

import com.kaspersky.kaspresso.screens.KScreen
import com.example.moviepicker.R
import com.example.moviepicker.ui.SearchFragment
import io.github.kakaocup.kakao.edit.KEditText
import io.github.kakaocup.kakao.text.KTextView


object SearchScreen : KScreen<SearchScreen>() {

    override val layoutId: Int = R.layout.fragment_search
    override val viewClass: Class<*> = SearchFragment::class.java

    val editTextSearch = KEditText { withId(R.id.editTextSearch) }
    val textView = KTextView { withId(R.id.textViewFindMovie) }

}