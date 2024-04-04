/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.signup.screen

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
import com.bluhabit.blu.android.presentation.authentication.signup.SignUpAction
import com.bluhabit.blu.android.presentation.authentication.signup.SignUpState
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
fun OtpSignUpScreen(
    modifier: Modifier = Modifier,
    state: SignUpState = SignUpState(),
    onBackPressed: () -> Unit,
    onAction: (SignUpAction) -> Unit
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        onAction(SignUpAction.OnCountDownStart)
    }

    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .safeDrawingPadding()
            .padding(horizontal = dimens.dp_16, vertical = dimens.dp_24),
    ) {
        Box(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "arrow back",
                modifier = modifier
                    .size(dimens.dp_24)
                    .align(Alignment.CenterStart)
                    .clickable {
                        onBackPressed()
                    }
            )
            Image(
                painter = painterResource(id = R.drawable.app_logo),
                contentDescription = "",
                modifier = modifier
                    .size(dimens.dp_24)
                    .align(Alignment.Center)
            )
        }
        Spacer(modifier = modifier.padding(bottom = dimens.dp_24))
        Text(
            text = if (state.otpAttempt >= 4) "Kode OTP salah 3 kali" else stringResource(id = R.string.title_header_otp),
            style = UwangTypography.BodyXL.SemiBold,
            color = UwangColors.Text.Main
        )
        Spacer(modifier = modifier.padding(bottom = 4.dp))
        Text(
            text =
            if (state.otpAttempt >= 4)
                stringResource(id = R.string.sign_up_otp_action_locked)
            else
                stringResource(id = R.string.description_header_otp, state.emailState),
            style = UwangTypography.BodySmall.Regular,
            color = UwangColors.Text.Secondary
        )
        Spacer(modifier = modifier.padding(bottom = dimens.dp_24))
        Box(
            modifier = modifier.fillMaxWidth(),
        ) {
            TextFieldOtp(
                modifier = modifier.fillMaxWidth(),
                enabled = state.otpAttempt < 4,
                length = 4,
                value = state.otpNumberState,
                state = state.otpNumberInputState,
                onDone = {
                    focusManager.clearFocus(true)
                    onAction(SignUpAction.OnVerifyOtp)
                },
                onChange = { value ->
                    onAction(SignUpAction.OnOtpChange(value = value))
                }
            )
        }
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        when {
            state.showButtonResendOtp -> {
                Text(
                    text = stringResource(id = R.string.label_teks_button_resend_otp),
                    style = UwangTypography.BodySmall.Medium,
                    color = UwangColors.State.Primary.Main,
                    modifier = Modifier
                        .clickable {
                            onAction(SignUpAction.OnResendOtp)
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
                        onAction(SignUpAction.OnSentOtpAlertVisibilityChange(false))
                    }
                } else {
                    AlertError(message = stringResource(id = R.string.label_alert_error)) {
                        onAction(SignUpAction.OnSentOtpAlertVisibilityChange(false))
                    }
                }
            }
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.label_button_otp),
                enabled = state.otpNumberState.length == 4
                        && state.otpAttempt < 4 && state.verifyOtpButtonEnabled
            ) {
                onAction(SignUpAction.OnVerifyOtp)
            }
        }
    }
}

@Preview
@Composable
fun PreviewOtpScreen() {
    UwangTheme {
        OtpSignUpScreen(
            onAction = {},
            onBackPressed = {}
        )
    }
}