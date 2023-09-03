/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.eureka.feature.dashboard

sealed interface DashboardEffect {
    object Nothing: DashboardEffect
    object CloseApp: DashboardEffect
}