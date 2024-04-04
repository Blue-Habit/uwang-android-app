/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.personalization

import android.graphics.Bitmap
import com.bluhabit.core.ui.components.textfield.TextFieldState
import com.bluhabit.core.ui.ext.Empty
import javax.annotation.concurrent.Immutable

@Immutable
data class PersonalizationState(

    //global
    val showLoading: Boolean = false,
    val currentScreen: Int = 0,
    // Create Username Screen
    val usernameState: TextFieldState = TextFieldState.None,
    val usernameValueState: String = String.Empty,
    // UploadPhotoProfileScreen
    val profileImage: Bitmap? = null,
    val showDialogChoice: Boolean = false,
    val uploadProfileSuccess: Boolean = false,
    // Choose Topic Screen
    val selectedTopicList: List<String> = listOf(),
    // Choose Level Screen [INTERMEDIATE,PRO,ADVANCE]
    val selectedLevel: String = ""
)