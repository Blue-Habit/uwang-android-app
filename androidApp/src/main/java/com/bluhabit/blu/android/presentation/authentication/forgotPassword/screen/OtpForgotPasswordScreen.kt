/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.forgotPassword.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluehabit.core.ui.R
import com.bluhabit.blu.android.presentation.authentication.forgotPassword.ForgotPasswordAction
import com.bluhabit.blu.android.presentation.authentication.forgotPassword.ForgotPasswordState
import com.bluhabit.blu.data.ext.toDateTime
import com.bluhabit.core.ui.components.alert.AlertError
import com.bluhabit.core.ui.components.alert.AlertSuccess
import com.bluhabit.core.ui.components.button.ButtonPrimary
import com.bluhabit.core.ui.components.textfield.TextFieldOtp
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography

@Composable
fun OtpForgotPasswordScreen(
    state: ForgotPasswordState = ForgotPasswordState(),
    onBackPressed: () -> Unit,
    onAction: (ForgotPasswordAction) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        onAction(ForgotPasswordAction.OnCountDownStart)
    }

    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
            .padding(horizontal = dimens.dp_16, vertical = dimens.dp_24)
            .verticalScroll(rememberScrollState()),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "arrow back",
                modifier = Modifier
                    .size(dimens.dp_24)
                    .align(Alignment.CenterStart)
                    .clickable {
                        onBackPressed()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "",
                modifier = Modifier
                    .size(dimens.dp_24)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        Text(
            text = if (state.otpAttempt >= 4) "Kode OTP salah 3 kali" else stringResource(id = R.string.title_header_otp),
            style = UwangTypography.BodyXL.SemiBold,
            color = UwangColors.Text.Main
        )
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text =
            if (state.otpAttempt >= 4)
                stringResource(id = R.string.caption_error_field_login_locked)
            else
                stringResource(id = R.string.description_header_otp, state.emailState),
            style = UwangTypography.BodySmall.Regular,
            color = UwangColors.Text.Secondary
        )
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        TextFieldOtp(
            modifier = Modifier.fillMaxWidth(),
            enabled = state.otpAttempt < 4,
            length = 4,
            value = state.otpNumberState,
            state = state.emailInputState,
            onDone = {
                focusManager.clearFocus(true)
                onAction(ForgotPasswordAction.OnVerifyOtp)
            },
            onChange = { value ->
                onAction(ForgotPasswordAction.OnOtpChange(value = value))
            }
        )
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        when {
            state.showButtonResendOtp -> {
                Text(
                    text = stringResource(id = R.string.label_teks_button_resend_otp),
                    style = UwangTypography.BodySmall.Medium,
                    color = UwangColors.State.Primary.Main,
                    modifier = Modifier
                        .clickable {
                            onAction(ForgotPasswordAction.OnResendOtp)
                        }
                )
            }

            !state.showButtonResendOtp -> {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(id = R.string.placeholder_teks_waiting_otp),
                        style = UwangTypography.BodySmall.Regular,
                        color = UwangColors.Text.Secondary
                    )
                    Text(
                        text = state.otpSentCountDown.toDateTime("mm:ss"),
                        style = UwangTypography.BodySmall.Medium,
                        color = UwangColors.Text.Secondary
                    )
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        Column(
            verticalArrangement = Arrangement.spacedBy(dimens.dp_24),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.otpSentAlertVisibility) {
                if (state.otpSentAlertSuccess) {
                    AlertSuccess(message = stringResource(id = R.string.label_alert_success)) {
                        onAction(ForgotPasswordAction.OnSentOtpAlertVisibilityChange(false))
                    }
                } else {
                    AlertError(message = stringResource(id = R.string.label_alert_error)) {
                        onAction(ForgotPasswordAction.OnSentOtpAlertVisibilityChange(false))
                    }
                }
            }
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.label_button_otp),
                enabled = state.otpNumberState.length == 4
                        && state.otpAttempt < 4
            ) {
                onAction(ForgotPasswordAction.OnVerifyOtp)
            }
        }
    }
}

@Preview
@Composable
fun OtpSignInScreenPreview() {
    UwangTheme {
        OtpForgotPasswordScreen(
            onBackPressed = {}
        )
    }
}