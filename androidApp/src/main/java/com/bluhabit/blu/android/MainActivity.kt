/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bluhabit.blu.android.presentation.authentication.forgotPassword.ForgotPasswordScreen
import com.bluhabit.blu.android.presentation.authentication.forgotPassword.ForgotPasswordViewModel
import com.bluhabit.blu.android.presentation.authentication.onboard.OnboardScreen
import com.bluhabit.blu.android.presentation.authentication.onboard.OnboardViewModel
import com.bluhabit.blu.android.presentation.authentication.personalization.PersonalizationViewModel
import com.bluhabit.blu.android.presentation.authentication.personalization.PersonalizeScreen
import com.bluhabit.blu.android.presentation.authentication.signin.SignInScreen
import com.bluhabit.blu.android.presentation.authentication.signin.SignInViewModel
import com.bluhabit.blu.android.presentation.authentication.signup.SignUpScreen
import com.bluhabit.blu.android.presentation.authentication.signup.SignUpViewModel
import com.bluhabit.blu.android.presentation.authentication.termAndCondition.TermAndConditionScreen
import com.bluhabit.blu.android.presentation.editprofile.EditProfileScreen
import com.bluhabit.blu.android.presentation.editprofile.EditProfileViewModel
import com.bluhabit.blu.android.presentation.home.HomeScreen
import com.bluhabit.blu.android.presentation.home.HomeViewModel
import com.bluhabit.core.ui.theme.UwangTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private var sseListener: SseListener? = null

    interface SseListener {
        fun onEvent()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navHostController = rememberNavController()
            UwangTheme(
                darkTheme = false
            ) {
                NavHost(
                    navController = navHostController,
                    startDestination = Routes.Onboard,
                ) {
                    composable(Routes.Onboard) {
                        val viewModel = hiltViewModel<OnboardViewModel>()
                        OnboardScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction
                        )
                    }
                    composable(Routes.TermAndCondition) {
                        TermAndConditionScreen(
                            onBackPressed = {
                                navHostController.navigateUp()
                            }
                        )
                    }
                    composable(Routes.SignIn) {
                        val viewModel = hiltViewModel<SignInViewModel>()
                        SignInScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction,
                        )
                    }
                    composable(Routes.SignUp) {
                        val viewModel = hiltViewModel<SignUpViewModel>()
                        SignUpScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction,
                        )
                    }
                    composable(Routes.ForgotPassword) {
                        val viewModel = hiltViewModel<ForgotPasswordViewModel>()
                        ForgotPasswordScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction
                        )
                    }
                    composable(Routes.Personalize) {
                        val viewModel = hiltViewModel<PersonalizationViewModel>()
                        PersonalizeScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction
                        )
                    }
                    composable(Routes.Home) {
                        val viewModel = hiltViewModel<HomeViewModel>()
                        HomeScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction
                        )
                    }
                    composable(Routes.EditProfile) {
                        val viewModel = hiltViewModel<EditProfileViewModel>()
                        EditProfileScreen(
                            navHostController = navHostController,
                            stateFlow = viewModel.state,
                            effectFlow = viewModel.onEffect,
                            onAction = viewModel::onAction
                        )
                    }
                }
            }
        }
    }

    fun addEventListener(listener: SseListener) {
        sseListener = listener
    }
}

