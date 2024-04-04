/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.core.ui.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
fun ProfileLevelOneNoInfoCard(
    modifier: Modifier = Modifier,
    onCardClick: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    BaseProfileCard(
        modifier = modifier,
        height = dimens.from(52.dp),
        onCardClick = onCardClick
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(dimens.dp_8),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxSize()
                .padding(dimens.dp_12),
        ) {
            Image(
                painter = painterResource(id = R.drawable.badge_level_1),
                contentDescription = "",
                modifier = Modifier
                    .width(dimens.dp_28)
                    .height(dimens.from(29.3.dp))
            )
            Text(
                text = stringResource(id = R.string.beginner_1),
                style = UwangTypography.BodySmall.Medium,
                color = UwangColors.Text.Main
            )
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
    }
}

@Preview
@Composable
fun ProfileLevelOneNoInfoCardPreview() {
    UwangTheme {
        ProfileLevelOneNoInfoCard(
            modifier = Modifier
                .padding(top = 56.dp, start = 16.dp, end = 16.dp)
                .safeDrawingPadding(),
        )
    }
}