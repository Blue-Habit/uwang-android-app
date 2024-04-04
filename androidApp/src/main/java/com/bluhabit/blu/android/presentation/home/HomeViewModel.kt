/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.bluhabit.blu.android.common.BaseViewModel
import com.bluhabit.blu.android.data.profile.domain.EditProfileUseCase
import com.bluhabit.blu.android.data.profile.domain.GetProfileUseCase
import com.bluhabit.blu.data.common.Response
import com.bluhabit.blu.data.common.executeAsFlow
import com.bluhabit.blu.data.ext.toDate
import com.bluhabit.blu.data.ext.toListOfType
import com.bluhabit.core.ui.ext.getMaxPointByLevel
import com.bluhabit.core.ui.ext.getMaxStepByLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase,
) : BaseViewModel<HomeState, HomeAction, HomeEffect>(HomeState()) {

    override fun onAction(action: HomeAction) {
        when (action) {
            is HomeAction.OnScreenChange -> updateState { copy(currentScreen = action.screen) }
            is HomeAction.OnBottomNavBadgeVisibilityChange ->
                updateState {
                    val newBottomNavigationItems = bottomNavigationItems.toMutableList()
                    val newBottomNavigationItem = bottomNavigationItems[action.index]
                        .copy(isBadgeVisible = action.visibility)
                    newBottomNavigationItems[action.index] = newBottomNavigationItem
                    copy(
                        bottomNavigationItems = newBottomNavigationItems
                    )
                }

            HomeAction.GetProfile -> getProfile()
        }
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
                                bioProfile = userProfile?.find { it.key == "bio" }?.value as String?,
                                websiteProfile = userProfile?.find { it.key == "link" }?.value as String?,
                                currentPoint = 0, // point saat ini
                                sizePoint = (userProfile?.find { it.key == "level" }?.value as Int?).getMaxPointByLevel() ?: 0,
                                topicList = (userProfile?.find { it.key == "topics" }?.value as String?)?.split(",") ?: listOf(),
                                completedStep = 0, // step personalisasi yang sudah di isi
                                sizeStep = (userProfile?.find { it.key == "level" }?.value as Int?).getMaxStepByLevel() ?: 0, // total step yang terdapat di personalisasi
                                sizePost = 0, // total postingan yang sudah dibuat
                                sizeFollowers = 0, // total followers yang dimiliki
                                sizeFollowing = 0, // total jumlah mengikuti
                                joinDate = createdAt.toDate("dd MMMM yyyy")
                            )
                        }
                    }
                }
            }
        }.collect()
    }
}