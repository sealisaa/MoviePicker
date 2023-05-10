package com.example.moviepicker.test

import com.example.moviepicker.MainActivity
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

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() = run {
        step("Beginning Scenario") {
            scenario(ProfileTransitionScenario())
        }
        step("Transition to Settings") {
            ProfileScreen {
                settingsButton {
                    isVisible()
                    click()
                }
            }
        }
        step("Check if russian locale is set") {
            SettingsScreen {
                inputLayout {
                    edit.containsText("Русский")
                }
            }
        }
        step("Check if Profile screen is in russian") {
            SettingsScreen {
                textViewTitle {
                    hasText("Настройки")
                }
                textViewLanguage {
                    hasText("Язык")
                }
                backButton {
                    isVisible()
                    click()
                }
            }
            ProfileScreen {
                textViewTitle {
                    hasText("Мои фильмы")
                }
                textViewStatistics {
                    containsText("Добавлено")
                }
                textViewAdded {
                    hasText("Избранное")
                }
            }

        }
        step("Transition to Search screen") {
            HomeScreen {
                searchFragmentButton {
                    isVisible()
                    click()
                }
            }
        }
        step("Check if Search screen is in russian") {
            SearchScreen {
                editTextSearch {
                    hasHint("Фильмы, сериалы")
                }
                textView {
                    hasText("Найти фильм")
                }
            }
        }
    }
}