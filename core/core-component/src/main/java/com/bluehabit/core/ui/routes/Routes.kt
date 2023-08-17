/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.core.ui.routes

import com.bluehabit.core.ui.R

object Routes {
    object Onboard {
        const val routeName = "onboarding"

        val itemsOnboarding = listOf(
            Pair(
                R.drawable.onboard_1,
                R.string.text_onboard_1
            ),
            Pair(
                R.drawable.onboard_2,
                R.string.text_onboard_2
            ),
            Pair(
                R.drawable.onboard_3,
                R.string.text_onboard_3
            ),
            Pair(
                R.drawable.onboard_2,
                R.string.text_onboard_4
            )
        )
    }

    object SignUp {
        const val routeName = "SignUp"
        const val currentScreenArg = "currentScreen"
    }

    object Auth {
        const val routeName = "auth"
    }

    object Home {
        const val routeName = "dashboard"
    }
}

