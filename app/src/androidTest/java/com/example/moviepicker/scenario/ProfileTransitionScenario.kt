package com.example.moviepicker.scenario

import com.example.moviepicker.screen.HomeScreen
import com.kaspersky.kaspresso.testcases.api.scenario.Scenario
import com.kaspersky.kaspresso.testcases.core.testcontext.TestContext

class ProfileTransitionScenario : Scenario() {

    override val steps: TestContext<Unit>.() -> Unit = {
        step("Transition to Profile Screen") {
            HomeScreen {
                profileFragmentButton {
                    isVisible()
                    click()
                }
            }
        }
    }
}