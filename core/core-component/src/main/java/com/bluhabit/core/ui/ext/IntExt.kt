/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.core.ui.ext

fun Int?.getMaxPointByLevel() = when(this) {
    1 -> 500
    else -> null
}

fun Int?.getMaxStepByLevel() = when(this) {
    1 -> 4
    else -> null
}