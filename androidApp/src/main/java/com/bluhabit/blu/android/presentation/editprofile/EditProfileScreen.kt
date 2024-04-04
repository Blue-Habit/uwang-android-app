/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.editprofile

import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bluhabit.blu.android.Routes
import com.bluhabit.blu.android.presentation.editprofile.screen.EditTopicScreen
import com.bluhabit.blu.android.presentation.editprofile.screen.InputEditProfileScreen
import com.bluhabit.core.ui.components.dialog.DialogLoading
import com.bluhabit.core.ui.components.sheet.PickProfileImageBottomSheet
import com.bluhabit.core.ui.ext.getBitmap
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

@Composable
fun EditProfileScreen(
    navHostController: NavHostController = rememberNavController(),
    stateFlow: Flow<EditProfileState> = flowOf(),
    effectFlow: Flow<EditProfileEffect> = flowOf(),
    onAction: (EditProfileAction) -> Unit = {},
) {
    LaunchedEffect(Unit) {
        onAction(EditProfileAction.OnGetProfile)
    }
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    val state by stateFlow.collectAsStateWithLifecycle(
        initialValue = EditProfileState()
    )
    val effect by effectFlow.collectAsStateWithLifecycle(initialValue = EditProfileEffect.None)
    val bottomSheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        confirmValueChange = {
            it != ModalBottomSheetValue.HalfExpanded
        },
        skipHalfExpanded = true
    )
    val scope = rememberCoroutineScope()
    val pickImageLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            scope.launch {
                bottomSheetState.hide()
            }
            if (uri != null) {
                val bitmap = uri.getBitmap(ctx.contentResolver)
                if (bitmap != null) {
                    onAction(EditProfileAction.OnImageSelected(bitmap))
                } else {
                    onAction(EditProfileAction.OnUploadProfileImageErrorVisibilityChange(visible = true))
                }
            } else {
                onAction(EditProfileAction.OnUploadProfileImageErrorVisibilityChange(visible = true))
            }
        }
    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap: Bitmap? ->
        scope.launch {
            bottomSheetState.hide()
        }
        if (bitmap != null) {
            onAction(EditProfileAction.OnImageSelected(bitmap))
        } else {
            onAction(EditProfileAction.OnUploadProfileImageErrorVisibilityChange(visible = true))
        }
    }
    LaunchedEffect(key1 = effect, block = {
        when (effect) {
            EditProfileEffect.NavigateToMain -> {
                navHostController.navigate(Routes.Home) {
                    launchSingleTop = true
                    popUpTo(Routes.EditProfile) {
                        inclusive = true
                    }
                }
            }

            EditProfileEffect.None -> {}
        }
    })

    fun goBack() {
        when {
            bottomSheetState.isVisible -> scope.launch {
                bottomSheetState.hide()
            }

            state.currentScreen == 0 -> navHostController.navigateUp()
            else -> onAction(EditProfileAction.OnScreenChange(state.currentScreen - 1))
        }
    }

    BackHandler {
        goBack()
    }
    DialogLoading(show = state.showLoading)
    ModalBottomSheetLayout(
        modifier = Modifier
            .background(UwangColors.Base.White)
            .safeDrawingPadding(),
        sheetState = bottomSheetState,
        sheetBackgroundColor = Color.White,
        sheetShape = RoundedCornerShape(
            topStart = dimens.dp_24,
            topEnd = dimens.dp_24
        ),
        sheetContent = {
            PickProfileImageBottomSheet(
                onTakePicture = {
                    cameraLauncher.launch()
                },
                onPickFromGallery = {
                    pickImageLauncher.launch("image/*")
                }
            )
        }
    ) {
        when (state.currentScreen) {
            0 -> InputEditProfileScreen(
                state = state,
                onAction = onAction,
                onBackPressed = {
                    goBack()
                },
                onPickProfileImageBottomSheetShow = {
                    scope.launch {
                        bottomSheetState.show()
                    }
                }
            )

            1 -> EditTopicScreen(
                state = state,
                onAction = onAction,
                onBackPressed = {
                    goBack()
                },
            )
        }
    }
}

@Preview
@Composable
fun EditProfileScreenPreview() {
    // ini bakal error kalau di view model ada constructor dari hilt dependency injection
    // solusi: block comment constructor yang ada di view model dan semua kode yg berhubungan dengannya
    val viewModel = viewModel<EditProfileViewModel>()
    UwangTheme {
        EditProfileScreen(
            stateFlow = viewModel.state,
            onAction = viewModel::onAction,
            effectFlow = viewModel.onEffect
        )
    }
}