/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.editprofile

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bluehabit.core.ui.R
import com.bluhabit.blu.android.common.BaseViewModel
import com.bluhabit.blu.android.data.authentication.domain.PersonalizeUploadProfilePictureUseCase
import com.bluhabit.blu.android.data.profile.domain.EditProfileUseCase
import com.bluhabit.blu.android.data.profile.domain.GetProfileUseCase
import com.bluhabit.blu.data.common.ResourceProvider
import com.bluhabit.blu.data.common.Response
import com.bluhabit.blu.data.common.executeAsFlow
import com.bluhabit.blu.data.ext.getProfileImageUrl
import com.bluhabit.core.ui.components.textfield.TextFieldState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val resourceProvider: ResourceProvider,
    private val getProfileUseCase: GetProfileUseCase,
    private val editProfileUseCase: EditProfileUseCase,
    private val uploadProfilePictureUseCase: PersonalizeUploadProfilePictureUseCase,
) : BaseViewModel<EditProfileState, EditProfileAction, EditProfileEffect>(
    EditProfileState()
) {
    override fun onAction(action: EditProfileAction) {
        when (action) {
            is EditProfileAction.OnImageSelected -> uploadImageProfile(bitmap = action.bitmap)

            is EditProfileAction.OnUploadProfileImageErrorVisibilityChange ->
                updateState { copy(uploadImageProfileErrorAlertVisibility = action.visible) }

            is EditProfileAction.OnUploadProfileImageSuccessVisibilityChange ->
                updateState { copy(uploadImageProfileSuccessAlertVisibility = action.visible) }

            is EditProfileAction.OnUsernameChange -> onUsernameChange(action.value)
            is EditProfileAction.OnFullNameChange -> onFullNameChange(action.value)
            is EditProfileAction.OnLinkChange -> updateState { copy(link = action.value) }
            is EditProfileAction.OnBioChange -> updateState {
                if (action.value.length <= 150) copy(
                    bio = action.value,
                    bioState = TextFieldState.None
                )
                else copy(
                    bioState = TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_bio_max))
                )
            }

            is EditProfileAction.OnRemoveSelectedTopic -> onRemoveSelectedTopic(action.selectedTopic)
            is EditProfileAction.OnScreenChange -> updateState { copy(currentScreen = action.screen) }
            EditProfileAction.OnEditNewTopic -> updateState {
                copy(
                    currentScreen = 1,
                    tempSelectedTopic = selectedTopic
                )
            }

            EditProfileAction.OnClearTempSelectedTopic -> updateState { copy(tempSelectedTopic = listOf()) }
            is EditProfileAction.OnAddTempSelectedTopic -> onAddTempSelectedTopic(action.selectedTopic)

            EditProfileAction.OnEditProfileSaved -> editProfile()
            EditProfileAction.OnEditTopicSaved -> updateState {
                copy(
                    currentScreen = 0,
                    selectedTopic = tempSelectedTopic,
                )
            }

            is EditProfileAction.OnRemoveTempSelectedTopic -> onRemoveTempSelectedTopic(action.selectedTopic)
            EditProfileAction.OnGetProfile -> getProfile()
        }
    }

    private fun uploadImageProfile(bitmap: Bitmap) = viewModelScope.launch {
        executeAsFlow {
            uploadProfilePictureUseCase.invoke(bitmap)
        }.onStart {
            updateState {
                copy(
                    showLoading = true,
                    uploadImageProfileErrorAlertVisibility = false,
                    uploadImageProfileSuccessAlertVisibility = false
                )
            }
        }.onEach { response ->
            updateState {
                when (response) {
                    is Response.Error -> copy(
                        showLoading = false,
                        uploadImageProfileErrorAlertVisibility = true
                    )

                    is Response.Result -> {
                        copy(
                            showLoading = false,
                            uploadImageProfileSuccessAlertVisibility = true,
                            imageProfileUrl = response.data.getProfileImageUrl()
                        )
                    }
                }
            }
        }.collect()
    }

    private fun getProfile() = viewModelScope.launch {
        executeAsFlow {
            getProfileUseCase.invoke()
        }.onStart {
            updateState { copy(showLoading = true) }
        }.onEach { response ->
            updateState { copy(showLoading = false) }
            when (response) {
                is Response.Error -> {
                    Log.e("error", "Code: ${response.code} Message: ${response.message}")
                }

                is Response.Result -> {
                    updateState {
                        with(response.data) {
                            copy(
                                username = username,
                                fullName = fullName,
                                imageProfileUrl = userProfile?.find { it.key == "profile-picture" }?.value as String?,
                                link = (userProfile?.find { it.key == "link" }?.value ?: "") as String,
                                bio = (userProfile?.find { it.key == "bio" }?.value ?: "") as String,
                                selectedTopic = (userProfile?.find { it.key == "topics" }?.value as String?)?.split(",") ?: listOf()
                            )
                        }
                    }
                }
            }
        }.collect()
    }

    private fun editProfile() = viewModelScope.launch {
        val newTopicList = state.value.selectedTopic
        val newTopicString = newTopicList.joinToString(",")
        executeAsFlow {
            editProfileUseCase.invoke(
                fullName = state.value.fullName,
                interestTopic = newTopicString,
                link = state.value.link,
                bio = state.value.bio,
                username = state.value.username
            )
        }.onStart {
            updateState { copy(showLoading = true) }
        }.onEach { response ->
            updateState { copy(showLoading = false) }
            when (response) {
                is Response.Error -> {
                    Log.e("error", "Code: ${response.code} Message: ${response.message}")
                }

                is Response.Result -> sendEffect(EditProfileEffect.NavigateToMain)
            }
        }.collect()
    }

    private fun onRemoveSelectedTopic(topic: String) {
        updateState {
            val newList = selectedTopic.toMutableList()
            newList.remove(topic)
            copy(selectedTopic = newList)
        }
    }

    private fun onAddTempSelectedTopic(topic: String) {
        updateState {
            val newList = tempSelectedTopic.toMutableList()
            newList.add(topic)
            copy(tempSelectedTopic = newList)
        }
    }

    private fun onRemoveTempSelectedTopic(topic: String) {
        updateState {
            val newList = tempSelectedTopic.toMutableList()
            newList.remove(topic)
            copy(tempSelectedTopic = newList)
        }
    }

    private fun onFullNameChange(fullName: String) {
        updateState {
            copy(
                fullName = fullName,
                fullNameState = when {
                    fullName.length < 3 ->
                        TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_name_min))

                    resourceProvider.isBadWord(fullName) ->
                        TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_name_badword))

                    username.length > 60 ->
                        TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_name_max))

                    else -> TextFieldState.None
                }
            )
        }
    }

    private fun onUsernameChange(username: String) {
        updateState {
            copy(
                username = username,
                usernameState = when {
                    username.length < 3 ->
                        TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_username_min))

                    resourceProvider.isBadWord(username) ->
                        TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_username_badword))

                    username.length > 60 ->
                        TextFieldState.Error(resourceProvider.getString(R.string.caption_error_field_username_max))

                    else -> TextFieldState.None
                }
            )
        }
    }
}