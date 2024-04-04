/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.data.profile.domain

import com.bluhabit.blu.android.data.profile.repositories.ProfileRepository
import com.bluhabit.blu.data.common.Response
import javax.inject.Inject

class EditProfileUseCase @Inject constructor(
    private val profileRepository: ProfileRepository
) {
    suspend operator fun invoke(
        fullName: String,
        interestTopic: String,
        link: String,
        bio: String,
        username: String,
    ) = when (val updateProfileImage = profileRepository.updateProfilePicture()) {
        is Response.Error -> updateProfileImage
        is Response.Result -> profileRepository.updateProfile(
            fullName = fullName,
            interestTopic = interestTopic,
            link = link,
            bio = bio,
            username = username
        )
    }
}