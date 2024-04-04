/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.data.ext

import com.bluhabit.blu.data.BuildConfig

fun String.getProfileImageUrl() = "${BuildConfig.STORAGE_BASE_URL}profile-picture/$this"