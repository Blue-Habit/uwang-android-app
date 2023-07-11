/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.eureka.data.authentication.domain

import com.bluehabit.eureka.data.authentication.repositories.SignInWithEmailRepository
import com.bluehabit.eureka.data.common.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class SignInWithEmailUseCase @Inject constructor(
    private val signInWithEmailRepository: SignInWithEmailRepository
) {
    operator fun invoke(email:String,password:String): Flow<Response<Any>> = flow {
        emit(Response.Loading)
        val result = signInWithEmailRepository.execute(email, password)
        emit(result)
    }.flowOn(Dispatchers.IO)
}