/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.eureka.feature.dashboard

import android.os.Parcelable
import app.trian.mvi.ui.extensions.Empty
import app.trian.mvi.ui.internal.contract.MviState
import com.bluehabit.eureka.data.task.datasource.remote.response.TaskResponse
import com.bluehabit.eureka.feature.dashboard.model.ItemTabListTask
import javax.annotation.concurrent.Immutable
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.RawValue

@Immutable
@Parcelize
data class DashboardState(
    val fullName: String = String.Empty,
    val inputSearch: String = String.Empty,
    val dashboardScreen: Int = 0,
    //list task
    val allTask: @RawValue List<TaskResponse> = listOf(),
    val tabsListTask: @RawValue List<ItemTabListTask> = listOf(
        ItemTabListTask(
            title = "Semua",
            action = DashboardAction.GetAllListTask
        ),
        ItemTabListTask(
            title = "Hari ini",
            action = DashboardAction.GetListTaskToday
        ),
        ItemTabListTask(
            title = "Besok",
            action = DashboardAction.GetListTaskTomorrow
        ),
        ItemTabListTask(
            title = "Minggu ini",
            action = DashboardAction.GetListTaskThisWeek
        ),
        ItemTabListTask(
            title = "Selesai",
            action = DashboardAction.GetListTaskByStatus()
        )
    ),
    //end
    val favoriteItemTask: @RawValue List<TaskResponse> = listOf(),
    val tabsHome: @RawValue List<ItemTabListTask> = listOf(
        ItemTabListTask(
            title = "Semua",
            action = DashboardAction.GetAllListTask
        ),
    ),
    val selectedTabIndex: Int = 0,
    override val effect: @RawValue DashboardEffect = DashboardEffect.Nothing
) : MviState<DashboardEffect>(), Parcelable

