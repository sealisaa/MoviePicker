package com.example.moviepicker.test

import androidx.test.ext.junit.rules.activityScenarioRule
import com.example.moviepicker.ui.MainActivity
import com.example.moviepicker.scenario.ProfileTransitionScenario
import com.example.moviepicker.screen.ProfileScreen
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test


class StatisticsTest : TestCase() {

    @get:Rule
    val activityRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() = run {
        step("Beginning Scenario") {
            scenario(ProfileTransitionScenario())
        }
        step("Compare statistics size with actual rv size") {
            ProfileScreen {
                textViewStatistics {
                    // by adding " " + number + " " we exclude the case when statistics contains number 67 or etc
                    containsText(" " + ProfileScreen.recycler.getSize().toString() + " ")
                }
            }
        }
    }
}