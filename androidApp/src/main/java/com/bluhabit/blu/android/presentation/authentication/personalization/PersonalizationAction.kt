/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.personalization

import android.graphics.Bitmap
import com.bluhabit.blu.android.presentation.authentication.personalization.screen.SelectedTopic

sealed interface PersonalizationAction {

    // Global
    data class OnScreenChange(
        val screen:Int=0
    ):PersonalizationAction
    object NextSkip:PersonalizationAction
    // Create Username Screen
    data class OnUsernameChange(
        val value: String
    ) : PersonalizationAction
    object CreateUsernameNextButton : PersonalizationAction
    // Upload Photo Profile Screen
    data class OnProfileImageChange(
        val value: Bitmap,
    ) : PersonalizationAction
    data class OnShowDialogChoice (
        val show: Boolean,
    ) : PersonalizationAction
    object UploadPhotoProfileNextButton : PersonalizationAction
    // Choose Topic Screen
    object OnClearSelectedList: PersonalizationAction
    data class OnAddSelectedList(
        val topic: SelectedTopic,
    ) : PersonalizationAction
    data class OnRemoveSelectedList(
        val topic: SelectedTopic,
    ) : PersonalizationAction
    object ChooseTopicNextButton : PersonalizationAction
    // Choose Level Screen
    data class OnSelectedLevelChange(
        val index: Int,
    ) : PersonalizationAction
    object ChooseLevelNextButton : PersonalizationAction
}