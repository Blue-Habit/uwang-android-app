/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.home

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.bluhabit.blu.android.Routes
import com.bluhabit.blu.android.presentation.home.component.MainScreen
import com.bluhabit.blu.android.presentation.home.component.NotificationScreen
import com.bluhabit.blu.android.presentation.home.component.PostScreen
import com.bluhabit.blu.android.presentation.home.component.ProfileScreen
import com.bluhabit.blu.android.presentation.home.component.SearchScreen
import com.bluhabit.core.ui.components.dialog.DialogLoading
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    navHostController: NavHostController = rememberNavController(),
    stateFlow: Flow<HomeState> = flowOf(),
    effectFlow: Flow<HomeEffect> = flowOf(),
    onAction: (HomeAction) -> Unit = {},
) {
    // This screen is Base Screen of Bottom Navigation
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    val state by stateFlow.collectAsStateWithLifecycle(initialValue = HomeState())
    val effect by effectFlow.collectAsState(initial = HomeEffect.None)
    var exitApp by remember { mutableStateOf(false) }


    DialogLoading(show = state.showLoading)

    Scaffold(
        modifier = Modifier
            .background(UwangColors.Base.White)
            .fillMaxSize()
            .safeDrawingPadding(),
        bottomBar = {
            BottomNavigationBar(
                state = state,
                effect = effect,
                onAction = onAction
            )
        }
    ) { paddingValues ->
        when (state.currentScreen) {
            0 -> MainScreen(
                paddingValues = paddingValues,
                state = state,
                onAction = onAction,
            )

            1 -> SearchScreen(
                paddingValues = paddingValues,
                state = state,
                onAction = onAction,
            )

            2 -> PostScreen(
                paddingValues = paddingValues,
                state = state,
                onAction = onAction,
            )

            3 -> NotificationScreen(
                paddingValues = paddingValues,
                state = state,
                onAction = onAction,
            )

            4 -> ProfileScreen(
                paddingValues = paddingValues,
                state = state,
                onAction = onAction,
                onEditProfile = {
                    navHostController.navigate(Routes.EditProfile) {
                        launchSingleTop = true
                    }
                }
            )
        }
    }

    fun goBack() {
        when {
            state.currentScreen > 0 -> onAction(HomeAction.OnScreenChange(0))
            state.currentScreen == 0 -> {
                if (exitApp) {
                    (ctx as Activity).finish()
                } else {
                    Toast.makeText(ctx, "Apakah Anda yakin ingin keluar aplikasi?", Toast.LENGTH_LONG).show()
                    exitApp = true
                }
            }
        }
    }

    LaunchedEffect(exitApp) {
        delay(3000)
        exitApp = false
    }

    BackHandler {
        goBack()
    }
}

@Composable
fun BottomNavigationBar(
    state: HomeState,
    effect: HomeEffect,
    onAction: (HomeAction) -> Unit
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)

    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Divider(
            thickness = dimens.from(1.dp),
            color = UwangColors.Text.Separator
        )
        BottomNavigation(
            modifier = Modifier
                .height(dimens.from(56.dp))
                .fillMaxWidth(),
            backgroundColor = UwangColors.Base.White
        ) {
            state.bottomNavigationItems.forEachIndexed { index, bottomNavigationItem ->
                val isSelected = state.currentScreen == index
                BottomNavigationItem(
                    selected = isSelected,
                    onClick = {
                        onAction(HomeAction.OnScreenChange(index))
                    },
                    icon = {
                        Box(
                            modifier = Modifier
                                .width(dimens.from(52.8.dp))
                                .height(dimens.from(40.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Box {
                                if (index < 4) {
                                    Image(
                                        modifier = Modifier
                                            .size(dimens.dp_24)
                                            .align(Alignment.Center),
                                        painter = painterResource(
                                            id = if (isSelected) (bottomNavigationItem.selectedIcon as Int)
                                            else (bottomNavigationItem.icon as Int)
                                        ),
                                        contentDescription = ""
                                    )
                                } else {
                                    val iconProfileModel = rememberAsyncImagePainter(
                                        model = when {
                                            state.imageProfileUrl != null -> state.imageProfileUrl
                                            else -> "https://r2.easyimg.io/shbq3yqpw/frame_37002.png"
                                        }
                                    )
                                    Image(
                                        painter = iconProfileModel,
                                        contentDescription = "",
                                        modifier = Modifier
                                            .size(dimens.dp_24)
                                            .clip(CircleShape)
                                            .border(
                                                width = if (isSelected) dimens.from(2.dp) else dimens.from(1.dp),
                                                color =
                                                if (isSelected) UwangColors.Text.Heading
                                                else Color(0xFF98A2B3),
                                                shape = CircleShape
                                            )
                                            .align(Alignment.Center)
                                    )
                                }
                                if (bottomNavigationItem.isBadgeVisible) {
                                    Box(
                                        modifier = Modifier
                                            .border(
                                                width =
                                                if (isSelected && index == 4) dimens.from(1.dp)
                                                else dimens.from(0.dp),
                                                shape = CircleShape,
                                                color = UwangColors.Base.White
                                            )
                                            .size(dimens.from(5.dp))
                                            .clip(CircleShape)
                                            .background(Color(0xFFDD2E2E))
                                            .align(Alignment.TopEnd)
                                    )
                                }
                            }
                        }
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    // ini bakal error kalau di view model ada constructor dari hilt dependency injection
    // solusi: block comment constructor yang ada di view model dan semua kode yg berhubungan dengannya
    val viewModel = viewModel<HomeViewModel>()
    UwangTheme {
        HomeScreen(
            stateFlow = viewModel.state,
            effectFlow = viewModel.onEffect,
            onAction = viewModel::onAction
        )
    }
}