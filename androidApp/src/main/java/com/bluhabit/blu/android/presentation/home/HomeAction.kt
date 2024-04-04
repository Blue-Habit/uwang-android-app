/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.home

import com.google.gson.annotations.SerializedName

sealed interface HomeAction {
    data class OnScreenChange(
        val screen: Int,
    ): HomeAction
    data class OnBottomNavBadgeVisibilityChange(
        val index: Int,
        val visibility: Boolean,
    ): HomeAction
    // Profile Screen
    object GetProfile: HomeAction
}