/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.publicprofile

data class PublicProfileState(
    val username: String = "",
    val fullName: String = "",
    val imageProfileUrl: String? = null,
    val bioProfile: String? = null,
    val websiteProfile: String? = null,
    val topicList: List<String> = listOf(),
    val sizePost: Int = 0,
    val sizeFollowers: Int = 0,
    val sizeFollowing: Int = 0,
    val joinDate: String = "",
    val isFollowed: Boolean = false,
)