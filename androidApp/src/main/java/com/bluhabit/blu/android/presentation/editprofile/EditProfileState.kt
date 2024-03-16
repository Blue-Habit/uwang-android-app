/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.editprofile

import android.graphics.Bitmap
import com.bluhabit.blu.android.presentation.authentication.personalization.screen.SelectedTopic
import com.bluhabit.core.ui.components.textfield.TextFieldState
import com.bluhabit.core.ui.ext.Empty

data class EditProfileState(
    val currentScreen: Int = 0,
    val showLoading: Boolean = false,
    // Input Edit Profile Screen
    val currentImageProfileUrl: String? = null,
    val newImageProfileUri: Bitmap? = null,
    val uploadImageProfileErrorAlertVisibility: Boolean = false,
    val uploadImageProfileSuccessAlertVisibility: Boolean = false,
    val username: String = String.Empty,
    val usernameState: TextFieldState = TextFieldState.None,
    val fullName: String = String.Empty,
    val fullNameState: TextFieldState = TextFieldState.None,
    val link: String = String.Empty,
    val linkState: TextFieldState = TextFieldState.None,
    val bio: String = String.Empty,
    val bioState: TextFieldState = TextFieldState.None,
    val selectedTopic: List<SelectedTopic> = listOf(),
    val tempSelectedTopic: List<SelectedTopic> = listOf(),
)