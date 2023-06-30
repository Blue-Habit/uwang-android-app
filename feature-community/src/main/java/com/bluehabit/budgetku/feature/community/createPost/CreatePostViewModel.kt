/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.budgetku.feature.community.createPost

import app.trian.mvi.ui.viewModel.MviViewModel
import com.bluehabit.budgetku.feature.community.createPost.CreatePostAction
import com.bluehabit.budgetku.feature.community.createPost.CreatePostIntent
import com.bluehabit.budgetku.feature.community.createPost.CreatePostState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
) : MviViewModel<CreatePostState, CreatePostIntent, CreatePostAction>(CreatePostState()) {


    override fun onAction(action: CreatePostAction) {
        when(action) {
            is CreatePostAction.ChangePostVisibility -> commit {
                copy(
                    postVisibility = action.postVisibility,
                    isSubmitEnabled = true
                )
            }
        }
    }

}