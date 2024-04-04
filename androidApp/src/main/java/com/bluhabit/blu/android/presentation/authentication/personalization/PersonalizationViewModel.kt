/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.personalization

import android.graphics.Bitmap
import androidx.lifecycle.viewModelScope
import com.bluehabit.core.ui.R
import com.bluhabit.blu.android.common.BaseViewModel
import com.bluhabit.blu.android.data.authentication.domain.PersonalizeUpdateProfileLevelUseCase
import com.bluhabit.blu.android.data.authentication.domain.PersonalizeUpdateProfilePictureUseCase
import com.bluhabit.blu.android.data.authentication.domain.PersonalizeUpdateProfileTopicsUseCase
import com.bluhabit.blu.android.data.authentication.domain.PersonalizeUpdateProfileUsernameUseCase
import com.bluhabit.blu.android.data.authentication.domain.PersonalizeUploadProfilePictureUseCase
import com.bluhabit.blu.data.common.ResourceProvider
import com.bluhabit.blu.data.common.Response
import com.bluhabit.blu.data.common.executeAsFlow
import com.bluhabit.core.ui.components.textfield.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class PersonalizationViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val personalizeUpdateProfileUsernameUseCase: PersonalizeUpdateProfileUsernameUseCase,
    private val personalizeUpdateProfilePictureUseCase: PersonalizeUpdateProfilePictureUseCase,
    private val personalizeUploadProfilePictureUseCase: PersonalizeUploadProfilePictureUseCase,
    private val personalizeUpdateProfileTopicsUseCase: PersonalizeUpdateProfileTopicsUseCase,
    private val personalizeUpdateProfileLevelUseCase: PersonalizeUpdateProfileLevelUseCase
) : BaseViewModel<PersonalizationState, PersonalizationAction, PersonalizationEffect>(
    PersonalizationState()
) {
    override fun onAction(action: PersonalizationAction) {
        when (action) {
            PersonalizationAction.NextSkip -> nextStep()
            is PersonalizationAction.OnScreenChange -> updateState { copy(currentScreen = action.screen) }
            is PersonalizationAction.OnUsernameChange -> onUsernameChange(action.value)
            PersonalizationAction.CreateUsernameNextButton -> submitUsername()
            is PersonalizationAction.OnProfileImageChange -> onProfilePictureSelected(action.value)
            is PersonalizationAction.OnShowDialogChoice -> updateState { copy(showDialogChoice = action.show) }
            PersonalizationAction.UploadPhotoProfileNextButton -> submitPhotoProfileLink()
            is PersonalizationAction.OnAddSelectedList -> onSelectedTopicsSelected(action.topic)
            PersonalizationAction.OnClearSelectedList -> onClearSelectedTopic()

            is PersonalizationAction.OnRemoveSelectedList -> onRemoveSelectedTopic(action.topic)

            PersonalizationAction.ChooseTopicNextButton -> submitTopic()
            // Choose Level Screen
            is PersonalizationAction.OnSelectedLevelChange -> onSelectedLevel(action.value)

            PersonalizationAction.ChooseLevelNextButton -> submitLevel()
        }
    }

    private fun onSelectedLevel(level: String) {
        updateState {
            copy(selectedLevel = level)
        }
    }

    private fun onRemoveSelectedTopic(topic: String) {
        updateState {
            val newList = selectedTopicList.toMutableList()
            newList.remove(topic)
            copy(selectedTopicList = newList)
        }
    }

    private fun onClearSelectedTopic() {
        updateState {
            val newList = selectedTopicList.toMutableList()
            newList.clear()
            copy(selectedTopicList = newList)
        }
    }

    private fun onSelectedTopicsSelected(
        topic: String
    ) {
        updateState {
            val newList = selectedTopicList.toMutableList()
            newList.add(topic)
            copy(selectedTopicList = newList)
        }
    }

    private fun onUsernameChange(username: String) {
        updateState {
            when {
                username.length < 3 -> copy(
                    usernameState = TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_username_min)),
                    usernameValueState = username,
                )

                resourceProvider.isBadWord(username) -> copy(
                    usernameState = TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_username_badword)),
                    usernameValueState = username,
                )

                username.length > 60 -> copy(
                    usernameState = TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_username_max))
                )

                else -> copy(
                    usernameState = TextFieldState.None,
                    usernameValueState = username,
                )
            }
        }
    }

    private fun submitUsername() = viewModelScope.launch {
        executeAsFlow { personalizeUpdateProfileUsernameUseCase(username = _state.value.usernameValueState) }
            .onStart {
                updateState { copy(showLoading = true) }
            }
            .onEach {
                updateState { copy(showLoading = false) }
                when (it) {
                    is Response.Error -> Unit
                    is Response.Result -> {
                        updateState { copy(currentScreen = 1) }
                    }
                }
            }
            .collect()
    }

    private fun onProfilePictureSelected(bitmap: Bitmap) = viewModelScope.launch {
        updateState { copy(profileImage = bitmap) }
        executeAsFlow { personalizeUploadProfilePictureUseCase(file = _state.value.profileImage!!) }
            .onStart {
                updateState { copy(showLoading = true) }
            }
            .onEach {
                when (it) {
                    is Response.Error -> {
                        updateState { copy(showLoading = false, uploadProfileSuccess = false) }
                    }

                    is Response.Result -> {
                        updateState { copy(showLoading = false, uploadProfileSuccess = true) }
                    }
                }
            }
            .collect()
    }

    private fun submitPhotoProfileLink() = viewModelScope.launch {
        executeAsFlow { personalizeUpdateProfilePictureUseCase() }
            .onStart {
                updateState { copy(showLoading = true) }
            }
            .onEach {
                updateState { copy(showLoading = false) }
                when (it) {
                    is Response.Error -> Unit
                    is Response.Result -> {
                        updateState { copy(currentScreen = 2) }
                    }
                }
            }
            .collect()
    }


    private fun submitTopic() = viewModelScope.launch {
        executeAsFlow { personalizeUpdateProfileTopicsUseCase(topics = _state.value.selectedTopicList.map { it }) }
            .onStart { updateState { copy(showLoading = true) } }
            .onEach {
                updateState { copy(showLoading = false) }
                when (it) {
                    is Response.Error -> Unit
                    is Response.Result -> {
                        updateState { copy(currentScreen = 3) }
                    }
                }
            }
            .collect()
    }


    private fun submitLevel() = viewModelScope.launch {
        executeAsFlow { personalizeUpdateProfileLevelUseCase(level = _state.value.selectedLevel) }
            .onStart { updateState { copy(showLoading = true) } }
            .onEach {
                updateState { copy(showLoading = false) }
                when (it) {
                    is Response.Error -> Unit
                    is Response.Result -> {
                        sendEffect(PersonalizationEffect.NavigateToMain)
                    }
                }
            }
            .collect()
    }

    private fun nextStep() = viewModelScope.launch {
        val currentStep = state.value.currentScreen
        if (currentStep >= 3) {
            // Submit Data
        } else {
            updateState { copy(currentScreen = currentStep + 1) }
        }
    }

}