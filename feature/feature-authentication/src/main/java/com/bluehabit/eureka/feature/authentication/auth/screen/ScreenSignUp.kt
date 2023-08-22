/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.eureka.feature.authentication.auth.screen

import android.util.Patterns
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.mvi.ui.extensions.from
import com.bluehabit.core.ui.R
import com.bluehabit.core.ui.components.alert.AlertError
import com.bluehabit.core.ui.components.button.ButtonFacebook
import com.bluehabit.core.ui.components.button.ButtonGoogle
import com.bluehabit.core.ui.components.button.ButtonPrimary
import com.bluehabit.core.ui.components.input.InputTextPrimary
import com.bluehabit.core.ui.theme.GaweanTheme
import com.bluehabit.core.ui.theme.Gray900
import com.bluehabit.core.ui.theme.Primary25
import com.bluehabit.core.ui.theme.Primary600
import com.bluehabit.core.ui.theme.Primary700
import com.bluehabit.eureka.feature.authentication.auth.AuthState

@Composable
fun ScreenSignUp(
    modifier: Modifier = Modifier,
    state: AuthState = AuthState(),
    onEmailChanged: (String) -> Unit = {},
    onSignUpEmail: () -> Unit = {},
    onSignUpGoogle: () -> Unit = {},
    onSignUpFacebook: () -> Unit = {},
    onResetAlert:()->Unit={},
    onShowTermCondition: () -> Unit = {},
    onShowPrivacyPolicy: () -> Unit = {}
) {
    val context = LocalContext.current
    fun isEmailValid(): Boolean {
        return state.emailSignUp.isNotEmpty() && Patterns
            .EMAIL_ADDRESS.matcher(state.emailSignUp).matches()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Primary25)
            .testTag("container_sign_up")
            .padding(
                horizontal = 18.dp,
                vertical = 18.dp
            ),
    ) {
        if (state.isError) {
            AlertError(message = state.errorMessage) {
                onResetAlert()
            }
        }
        Spacer(modifier = modifier.height(10.dp))
        InputTextPrimary(
            label = stringResource(id = R.string.text_label_input_email_screen_auth),
            value = state.emailSignUp,
            placeholder = stringResource(id = R.string.text_placeholder_input_email_screen_auth),
            onChange = onEmailChanged,
            enabled = true
        )
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = modifier.height(16.dp))
            ButtonPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(id = R.string.text_button_signup_with_email_screen_auth),
                enabled = isEmailValid(),
                onClick = onSignUpEmail
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp.from(context = context), Alignment.Start),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.line_4),
                    contentDescription = "line"
                )
                Text(
                    text = stringResource(R.string.text_or),
                    style = MaterialTheme.typography.caption,
                    lineHeight = 18.sp,
                    fontWeight = FontWeight.W400,
                    color = Primary600,
                )
                Image(
                    painter = painterResource(id = R.drawable.line_4),
                    contentDescription = "line"
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                ButtonGoogle(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.text_button_signup_with_google_screen_auth),
                    enabled = true,
                    onClick = onSignUpGoogle
                )
                ButtonFacebook(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 10.dp),
                    text = stringResource(id = R.string.text_button_signup_with_facebook_screen_auth),
                    enabled = true,
                    onClick = onSignUpFacebook
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                color = Gray900,
                                fontStyle = MaterialTheme.typography.body1.fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        ) {
                            append(stringResource(id = R.string.text_term_and_condition_title_screen_auth))
                        }
                        append(" ")
                        withStyle(
                            style = SpanStyle(
                                color = Primary700,
                                fontStyle = MaterialTheme.typography.body1.fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        ) {
                            append(stringResource(id = R.string.text_term_and_condition_screen_auth))
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Gray900,
                                fontStyle = MaterialTheme.typography.body1.fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        ) {
                            append(stringResource(id = R.string.text_and_screen_auth))
                        }
                        withStyle(
                            style = SpanStyle(
                                color = Primary700,
                                fontStyle = MaterialTheme.typography.body1.fontStyle,
                                fontWeight = FontWeight.W400
                            )
                        ) {
                            append(stringResource(id = R.string.text_privacy_police_screen_auth))
                        }
                    },
                    textAlign = TextAlign.Center,
                )
            }
        }
    }

}

@Preview
@Composable
fun PreviewScreenSignUp() {
    GaweanTheme {
        ScreenSignUp()
    }
}