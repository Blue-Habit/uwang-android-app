/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluehabit.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import app.trian.mvi.ui.extensions.dashedBorder
import com.bluehabit.core.ui.BaseMainApp
import com.bluehabit.core.ui.theme.Grey100
import com.bluehabit.core.ui.theme.Grey300
import com.bluehabit.core.ui.theme.Grey700

@Composable
fun ItemAccountPlaceholder(
    onClick: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val currentWidth = ctx
        .resources
        .displayMetrics.widthPixels.dp /
            LocalDensity.current.density
    val cardWidth = (currentWidth / 2) - 45.dp

    Column(
        modifier = Modifier
            .width(cardWidth)
            .height(cardWidth + 25.dp)
            .clip(MaterialTheme.shapes.medium)
            .clickable(
                enabled = true,
                onClick = onClick
            )
            .background(
                Color.Transparent
            )
            .dashedBorder(
                width = 1.dp,
                shape = MaterialTheme.shapes.medium,
                color = Grey300,
                on=5.dp,
                off=5.dp
            )
            .padding(
                vertical = 16.dp,
                horizontal = 16.dp
            ),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Grey100)
                .padding(
                    6.dp
                )
        ) {
            Icon(
                imageVector = Icons.Outlined.Add,
                contentDescription = "",
                modifier = Modifier
                    .align(Alignment.Center)
            )
        }
        Column {
            Text(
                text = "Tambah Akun",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                color = Grey700
            )
            Text(
                text = "Manual",
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.primary,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }

    }
}

@Preview
@Composable
fun PreviewItemAccountPlaceholder() {
    BaseMainApp {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                item{
                    ItemAccountPlaceholder(
                    )
                }
            }
        )
    }
}