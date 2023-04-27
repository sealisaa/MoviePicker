package com.example.moviepicker.screen

import com.example.moviepicker.SettingsFragment
import com.example.moviepicker.R
import com.kaspersky.kaspresso.screens.KScreen
import io.github.kakaocup.kakao.edit.KTextInputLayout
import io.github.kakaocup.kakao.text.KButton
import io.github.kakaocup.kakao.text.KTextView

object SettingsScreen : KScreen<SettingsScreen>() {

    override val layoutId: Int = R.layout.fragment_settings
    override val viewClass: Class<*> = SettingsFragment::class.java

    val inputLayout = KTextInputLayout { withId(R.id.languageOptions) }
    val textViewTitle = KTextView { withId(R.id.textViewSettings) }
    val textViewLanguage = KTextView { withId(R.id.textView) }
    val backButton = KButton { withId(R.id.backButton) }
}