/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.authentication.signin.screen

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
import com.bluhabit.blu.android.common.toDateTime
import com.bluhabit.blu.android.presentation.authentication.signin.SignInAction
import com.bluhabit.blu.android.presentation.authentication.signin.SignInState
import com.bluhabit.core.ui.components.alert.AlertError
import com.bluhabit.core.ui.components.alert.AlertSuccess
import com.bluhabit.core.ui.components.button.ButtonPrimary
import com.bluhabit.core.ui.components.textfield.TextFieldOtp
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography

@Composable
fun OtpSignInScreen(
    state: SignInState = SignInState(),
    onBackPressed: () -> Unit,
    onAction: (SignInAction) -> Unit = {},
) {
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit) {
        onAction(SignInAction.OnCountDownStart)
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
                        // On Back Pressed
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
            text = if (state.isAccountLocked) "Kode OTP salah 3 kali" else stringResource(id = R.string.title_header_otp),
            style = UwangTypography.BodyXL.SemiBold,
            color = UwangColors.Text.Main
        )
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text =
            if (state.isAccountLocked)
                stringResource(id = R.string.caption_error_field_login_locked)
            else
                stringResource(id = R.string.description_header_otp, "johndoe@gmail.com"),
            style = UwangTypography.BodySmall.Regular,
            color = UwangColors.Text.Secondary
        )
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        Box(
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextFieldOtp(
                modifier = Modifier.align(Alignment.Center),
                enabled = state.otpNumberEnabled && !state.isAccountLocked,
                length = 4,
                value = state.otpNumberState,
                error = state.otpNumberError,
                onDone = {
                    focusManager.clearFocus(true)
                    onAction(SignInAction.OnVerifyOtp)
                },
                onChange = { value ->
                    onAction(SignInAction.OnOtpChange(value = value))
                }
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 5.dp))
        if (state.otpNumberError) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.alert_triangle),
                    contentDescription = "",
                    modifier = Modifier
                        .size(dimens.dp_16)
                )
                Spacer(modifier = Modifier.padding(end = dimens.dp_8))
                Text(
                    text = "Kode OTP salah",
                    style = UwangTypography.LabelMedium.Regular,
                    color = UwangColors.State.Error.Main
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        when {
            state.isAccountLocked -> {
                Text(
                    text = stringResource(id = R.string.placeholder_teks_hour_otp),
                    style = UwangTypography.BodySmall.Regular,
                    color = UwangColors.Text.Secondary
                )
            }

            state.otpSentLimit -> {
                Text(
                    text = stringResource(id = R.string.placeholder_teks_many_otp),
                    style = UwangTypography.BodySmall.Regular,
                    color = UwangColors.Text.Secondary
                )
            }

            (state.otpSentCountDown > 0) -> {
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

            else -> {
                Text(
                    text = stringResource(id = R.string.label_teks_button_resend_otp),
                    style = UwangTypography.BodySmall.Medium,
                    color = UwangColors.State.Primary.Main,
                    modifier = Modifier
                        .clickable {
                            onAction(SignInAction.OnResentOtp)
                        }
                )
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
                        onAction(SignInAction.OnSentOtpAlertVisibilityChange(false))
                    }
                } else {
                    AlertError(message = stringResource(id = R.string.label_alert_error)) {
                        onAction(SignInAction.OnSentOtpAlertVisibilityChange(false))
                    }
                }
            }
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.label_button_otp),
                enabled = state.verifyOtpButtonEnabled && !state.isAccountLocked
            ) {
                onAction(SignInAction.OnVerifyOtp)
            }
        }
    }
}

@Preview
@Composable
fun OtpSignInScreenPreview() {
    UwangTheme {
        OtpSignInScreen(
            onBackPressed = {}
        )
    }
}