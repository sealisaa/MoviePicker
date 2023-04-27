package com.example.moviepicker

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.moviepicker.screens.GenresScreen
import com.example.moviepicker.screens.HomeScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Assert
import org.junit.Rule
import org.junit.Test

class GenresListTest : TestCase() {
    private val genres = listOf(
        "Мультфильмы",
        "Триллеры",
        "Комедии",
        "Ужасы"
    )

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun checkGenresScreen() = run {
        step("Open genres screen") {
            HomeScreen {
                goToGenres()
            }
        }
        step("Check genres count") {
            GenresScreen {
                Assert.assertEquals(genres.size, rvGenres.getSize())
            }
        }
        step("Check genres list") {
            GenresScreen {
                rvGenres {
                    for (i in 1 until genres.size) {
                        childAt<GenresScreen.GenreScreen>(i) {
                            genreButton.isVisible()
                            genreButton.hasText(genres[i])
                        }
                    }
                }
            }
        }
    }
}