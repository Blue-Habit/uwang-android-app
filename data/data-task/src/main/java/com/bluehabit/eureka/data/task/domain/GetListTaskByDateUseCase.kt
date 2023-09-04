/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.eureka.data.task.domain

import com.bluehabit.eureka.data.common.Response
import com.bluehabit.eureka.data.model.BasePagingResponse
import com.bluehabit.eureka.data.task.datasource.remote.response.TaskResponse
import com.bluehabit.eureka.data.task.repositories.TaskRepository
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import javax.inject.Inject

class GetListTaskByDateUseCase @Inject constructor(
    private val taskRepository: TaskRepository
) {
    suspend operator fun invoke(
        from: LocalDate,
        to: LocalDate
    ): Response<BasePagingResponse<TaskResponse>> {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
       return taskRepository.getListTaskByDate(
            from = from.format(formatter),
            to = to.format(formatter)
        )
    }
}