/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.data.authentication.datasource.remote.request

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class SignUpBasicRequest(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String
)
