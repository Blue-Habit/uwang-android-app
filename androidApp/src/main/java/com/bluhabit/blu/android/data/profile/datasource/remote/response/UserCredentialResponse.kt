/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.data.profile.datasource.remote.response

import com.google.gson.annotations.SerializedName

data class UserCredentialResponse(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("email")
	val email: String,

	@field:SerializedName("full_name")
	val fullName: String,

	@field:SerializedName("username")
	val username: String,

	@field:SerializedName("dateOfBirth")
	val dateOfBirth: String,

	@field:SerializedName("authProvider")
	val authProvider: String,

	@field:SerializedName("status")
	val status: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("updatedAt")
	val updatedAt: String,

	@field:SerializedName("deleted")
	val deleted: Boolean,

	@field:SerializedName("user_profile")
	val userProfile: List<UserProfile>?,
)

data class UserProfile(

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("value")
	val value: Any,

	@field:SerializedName("key")
	val key: String
)
