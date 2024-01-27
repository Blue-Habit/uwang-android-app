/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.signup

import javax.annotation.concurrent.Immutable

@Immutable
data class SignUpState(
    //global
    // 0 == sing up 1 == otp
    val currentScreen: Int = 0,
    //sign up
    val emailState: String = "",
    val emailError: Boolean = false,
    val emailErrorText: String = "",
    val passwordState: String = "",
    val passwordVisibility: Boolean = false,
    val passwordError: Boolean = false,
    val passwordErrorText: String = "",
    val passwordConfirmationState: String = "",
    val passwordConfirmationVisibility: Boolean = false,
    val passwordConfirmationError: Boolean = false,
    val passwordConfirmationErrorText: String = "",
    val signUpButtonEnabled: Boolean = true,

    // Otp Sign Up Screen
    val otpSentCountDown: Long = 120_000L,
    val otpNumberState: String = "",
    val otpNumberError: Boolean = false,
    val otpNumberEnabled: Boolean = true,
    val otpSentAlertVisibility: Boolean = false,
    val otpSentAlertSuccess: Boolean = false,
    val otpSentLimit: Boolean = false,
    val isAccountLocked: Boolean = false,
    val verifyOtpButtonEnabled: Boolean = true,
)