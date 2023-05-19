package com.example.moviepicker.test

import com.example.moviepicker.ui.MainActivity
import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.moviepicker.scenario.ProfileTransitionScenario
import com.example.moviepicker.screen.HomeScreen
import com.example.moviepicker.screen.ProfileScreen
import com.example.moviepicker.screen.SearchScreen
import com.example.moviepicker.screen.SettingsScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test



class LocaleTest : TestCase() {

    object Text {
        const val RUSSIAN = "Русский"
        const val SETTINGS = "Настройки"
        const val LANGUAGE = "Язык"
        const val MY_MOVIES = "Мои фильмы"
        const val ADDED = "Добавлено"
        const val FAVOURITES = "Избранное"
        const val MOVIES_SERIES = "Фильмы, сериалы"
        const val FIND_MOVIE = "Найти фильм"
    }

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() = run {
        step("Beginning Scenario") {
            scenario(ProfileTransitionScenario())
        }
        step("Transition to Settings") {
            ProfileScreen {
                simpleClick(settingsButton)
            }
        }
        step("Check if russian locale is set") {
            SettingsScreen {
                inputLayout {
                    edit.containsText(Text.RUSSIAN)
                }
            }
        }
        step("Check if Profile screen is in russian") {
            SettingsScreen {
                textViewTitle {
                    hasText(Text.SETTINGS)
                }
                textViewLanguage {
                    hasText(Text.LANGUAGE)
                }
                simpleClick(backButton)
            }
            ProfileScreen {
                textViewTitle {
                    hasText(Text.MY_MOVIES)
                }
                textViewStatistics {
                    containsText(Text.ADDED)
                }
                textViewAdded {
                    hasText(Text.FAVOURITES)
                }
            }

        }
        step("Transition to Search screen") {
            HomeScreen {
                simpleClick(searchFragmentButton)
            }
        }
        step("Check if Search screen is in russian") {
            SearchScreen {
                editTextSearch {
                    hasHint(Text.MOVIES_SERIES)
                }
                textView {
                    hasText(Text.FIND_MOVIE)
                }
            }
        }
    }
}