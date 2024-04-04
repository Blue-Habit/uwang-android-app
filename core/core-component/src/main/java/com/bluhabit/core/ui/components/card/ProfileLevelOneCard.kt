/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.core.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluehabit.core.ui.R
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography

@Composable
fun ProfileLevelOneCard(
    modifier: Modifier = Modifier,
    currentPoint: Int = 0,
    sizePoint: Int = 0,
    onCardClick: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)

    BaseProfileCard(
        modifier = modifier,
        onCardClick = onCardClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimens.dp_12),
            verticalArrangement = Arrangement.spacedBy(dimens.from(4.dp))
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(dimens.dp_8),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth(),
            ) {
                Image(
                    painter = painterResource(id = R.drawable.badge_level_1),
                    contentDescription = "",
                    modifier = Modifier
                        .width(dimens.dp_28)
                        .height(dimens.from(29.3.dp))
                )
                Column {
                    Text(
                        text = stringResource(id = R.string.beginner_1),
                        style = UwangTypography.BodySmall.Medium,
                        color = UwangColors.Text.Main
                    )
                    Text(
                        text = stringResource(id = R.string.remaining_points, sizePoint - currentPoint),
                        style = UwangTypography.BodyXS.Regular,
                        color = UwangColors.Text.Secondary
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = "",
                    tint = UwangColors.Text.Secondary,
                    modifier = Modifier
                        .padding(
                            horizontal = dimens.from(9.dp),
                            vertical = dimens.from(6.dp)
                        )
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                LinearProgressIndicator(
                    progress = 0f,
                    modifier = Modifier
                        .clip(CircleShape)
                        .fillMaxWidth()
                        .height(dimens.from(24.dp))
                        .drawWithContent {
                            drawContent()
                            drawRoundRect(
                                Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xFF3167E8),
                                        Color(0xFF004CFF),
                                        Color(0xFF1041B7),
                                    )
                                ),
                                blendMode = BlendMode.SrcAtop,
                                size = Size(width = size.width * (currentPoint.toFloat() / sizePoint.toFloat()), height =  size.height),
                                cornerRadius = CornerRadius(100f, 100f)
                            )
                        },
                    backgroundColor = UwangColors.State.Primary.Surface,
                )
                Text(
                    text = "$currentPoint/$sizePoint",
                    style = UwangTypography.LabelSmall.Regular,
                    color = UwangColors.Base.White,
                    modifier = Modifier
                        .align(Alignment.Center)
                )
                Box(
                    modifier = Modifier
                        .size(dimens.dp_24)
                        .clip(CircleShape)
                        .background(UwangColors.State.Primary.Main)
                        .border(
                            width = dimens.from(2.dp), brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF4271E3),
                                    Color(0xFF2553C3),
                                )
                            ), shape = CircleShape
                        )
                        .align(Alignment.CenterStart),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "1",
                        style = UwangTypography.BodySmall.Medium,
                        color = UwangColors.Base.White
                    )
                }
                Box(
                    modifier = Modifier
                        .size(dimens.dp_24)
                        .clip(CircleShape)
                        .background(UwangColors.State.Primary.Main)
                        .border(
                            width = dimens.from(2.dp), brush = Brush.linearGradient(
                                colors = listOf(
                                    Color(0xFF4271E3),
                                    Color(0xFF2553C3),
                                )
                            ), shape = CircleShape
                        )
                        .align(Alignment.CenterEnd),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "2",
                        style = UwangTypography.BodySmall.Medium,
                        color = UwangColors.Base.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ProfileLevelCardPreview() {
    UwangTheme {
        ProfileLevelOneCard(
            modifier = Modifier
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .safeDrawingPadding(),
            currentPoint = 10,
            sizePoint = 100
        )
    }
}