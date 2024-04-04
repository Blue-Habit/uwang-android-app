/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.core.ui.components.sheet

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bluehabit.core.ui.R
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTypography

@Composable
fun DetailInterestTopicsBottomSheet(
    topicList: List<String>,
    onTopicClicked: (index: Int, topic: String) -> Unit,
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(dimens.dp_16),
        contentPadding = PaddingValues(
            vertical = dimens.dp_24,
            horizontal = dimens.dp_16
        )
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Divider(
                    modifier = Modifier
                        .width(dimens.from(54.dp))
                        .height(dimens.from(4.dp))
                        .clip(RoundedCornerShape(dimens.dp_16)),
                    color = UwangColors.Text.Secondary
                )
            }
        }
        item {
            Text(
                text = stringResource(id = R.string.financial_interest_topics),
                style = UwangTypography.BodyLarge.SemiBold,
                color = UwangColors.Text.Main
            )
        }
        itemsIndexed(
            items = topicList,
            key = { index, _ ->
                index
            }
        ) { index, topic ->
            Text(
                text = topic,
                style = UwangTypography.BodyMedium.Regular,
                color = UwangColors.Text.Main,
                modifier = Modifier
                    .padding(
                        bottom = dimens.from(
                            // 16 + 4
                            if (index != (topicList.size - 1)) 4.dp else 0.dp
                        )
                    )
                    .clip(shape = RoundedCornerShape(dimens.from(4.dp)))
                    .clickable {
                        onTopicClicked(index, topic)
                    }
            )
        }
    }
}