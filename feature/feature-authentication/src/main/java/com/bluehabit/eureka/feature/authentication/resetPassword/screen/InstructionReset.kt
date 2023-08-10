/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.eureka.feature.authentication.resetPassword.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.trian.mvi.ui.extensions.from
import com.bluehabit.core.ui.theme.GaweTheme
import com.bluehabit.core.ui.theme.Gray900
import com.bluehabit.core.ui.theme.Primary600
import com.bluehabit.eureka.feature.authentication.R

@Composable
fun InstructionReset(
    openEmail: () -> Unit = {},
    tryAgain: () -> Unit = {},
) {
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(
                vertical = 28.dp.from(context = context),
                horizontal = 26.dp.from(context = context),
            )
            .background(Color.White),

        verticalArrangement = Arrangement.spacedBy(95.dp.from(context = context)), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .padding(
                    top = 28.dp.from(context = context),
                ),
            verticalArrangement = Arrangement.spacedBy(24.dp.from(context = context)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Image(
                painter = painterResource(
                    id = com.bluehabit.core.ui.R.drawable.gawean_logo
                ),
                contentDescription = "logo"
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp.from(context = context)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Instruksi Terkirim!",
                    style = MaterialTheme.typography.h6,
                    lineHeight = 30.sp,
                    fontWeight = FontWeight.W600,
                    textAlign = TextAlign.Center,
                    color = Gray900
                )
                Text(
                    text = "Cek email anda dan klik link pada instruksi tersebut.",
                    style = MaterialTheme.typography.body2,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Center,
                    color = Gray900
                )
            }
            Column(
                Modifier.padding(30.dp)
            ) {
                Image(
                    painter = painterResource(
                        id = com.bluehabit.core.ui.R.drawable.hero_intruction_reset_password
                    ),
                        contentDescription = "logo"
                )
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(32.dp.from(context = context)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = openEmail, modifier = Modifier
                    .width(320.dp)
                    .height(48.dp)
                    .background(
                        color = Color(0xFF7F56D9),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
            ) {
                Text(
                    text = "Buka Email",
                    color = Color(0xFFFFFFFF)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp.from(context = context)),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Tidak menerima email? Coba cek spam",
                    style = MaterialTheme.typography.caption,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Center,
                    color = Gray900
                )
                Text(
                    text = "atau",
                    style = MaterialTheme.typography.caption,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Center,
                    color = Gray900
                )
                Text(
                    modifier = Modifier.clickable {
                        tryAgain()
                    },
                    text = "coba lagi dengan alamat email yang lain.",
                    style = MaterialTheme.typography.body2,
                    lineHeight = 20.sp,
                    fontWeight = FontWeight.W400,
                    textAlign = TextAlign.Center,
                    color = Primary600
                )
            }
        }
    }
}

@Composable
@Preview(widthDp = 384, heightDp = 854)
fun DefaultPreview() {
    GaweTheme(darkTheme = false) {
        InstructionReset()
    }
}