/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.home

import com.bluehabit.core.ui.R

data class HomeState(
    val currentScreen: Int = 0,
    val showLoading: Boolean = false,
    val bottomNavigationItems: List<BottomNavigationItem> = listOf(
        BottomNavigationItem(
            icon = R.drawable.ic_home,
            selectedIcon = R.drawable.ic_home_fill,
            isBadgeVisible = true
        ),
        BottomNavigationItem(
            icon = R.drawable.ic_search,
            selectedIcon = R.drawable.ic_search_fill,
            isBadgeVisible = false
        ),
        BottomNavigationItem(
            icon = R.drawable.ic_add_square,
            selectedIcon = R.drawable.ic_add_square_fill,
            isBadgeVisible = false
        ),
        BottomNavigationItem(
            icon = R.drawable.ic_notification,
            selectedIcon = R.drawable.ic_notification_fill,
            isBadgeVisible = false
        ),
        BottomNavigationItem(
            icon = {},
            selectedIcon = {},
            isBadgeVisible = true
        ),
    ),
    // Main
    val username: String = "",
    val fullName: String? = null,
    val imageProfileUrl: String? = null,
    val bioProfile: String? = null,
    val websiteProfile: String? = null,
    val currentPoint: Int = 0,
    val sizePoint: Int = 500,
    val topicList: List<String> = listOf(),
    val completedStep: Int = 0,
    val sizeStep: Int = 4,
    val sizePost: Int = 0,
    val sizeFollowers: Int = 0,
    val sizeFollowing: Int = 0,
    val joinDate: String? = null
)

data class BottomNavigationItem(
    val icon: Any,
    val selectedIcon: Any,
    val isBadgeVisible: Boolean,
)
