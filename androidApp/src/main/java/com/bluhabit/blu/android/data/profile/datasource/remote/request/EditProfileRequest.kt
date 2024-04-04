/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.data.profile.datasource.remote.request

import com.google.gson.annotations.SerializedName

data class EditProfileRequest(

    @field:SerializedName("full_name")
    val fullName: String,

    @field:SerializedName("interest_topic")
    val interestTopic: String,

    @field:SerializedName("link")
    val link: String,

    @field:SerializedName("bio")
    val bio: String,

    @field:SerializedName("username")
    val username: String
)