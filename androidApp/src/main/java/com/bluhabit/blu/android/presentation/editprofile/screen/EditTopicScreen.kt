/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.editprofile.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bluehabit.core.ui.R
import com.bluhabit.blu.android.presentation.editprofile.EditProfileAction
import com.bluhabit.blu.android.presentation.editprofile.EditProfileState
import com.bluhabit.core.ui.components.chip.RoundedChip
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun EditTopicScreen(
    state: EditProfileState = EditProfileState(),
    onAction: (EditProfileAction) -> Unit = {},
    onBackPressed: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    val topicList = stringArrayResource(id = R.array.topic_list)

    Column(
        modifier = Modifier
            .background(UwangColors.Base.White)
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(vertical = dimens.dp_16)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = dimens.dp_16)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = "",
                tint = Color(0xFF0A0A0A),
                modifier = Modifier
                    .clickable(onClick = onBackPressed)
            )
            Text(
                text = stringResource(id = R.string.edit_topic),
                style = UwangTypography.BodyMedium.SemiBold,
                color = UwangColors.Text.Main
            )
            Icon(
                painter = painterResource(id = R.drawable.ic_check),
                contentDescription = "",
                tint = Color(0xFF0A0A0A),
                modifier = Modifier
                    .size(dimens.dp_24)
                    .clickable {
                        onAction(EditProfileAction.OnEditTopicSaved)
                    }
            )
        }
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
        Text(
            text = stringResource(id = R.string.title_selected_topic),
            style = UwangTypography.BodyXL.SemiBold,
            color = UwangColors.Text.Main,
            modifier = Modifier
                .padding(horizontal = dimens.dp_16)
        )
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_16))
        if (state.tempSelectedTopic.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(dimens.dp_8),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = dimens.dp_16)
            ) {
                state.tempSelectedTopic
                    .forEach {
                        RoundedChip(
                            text = it,
                            selected = true,
                            height = dimens.dp_36,
                            verticalPadding = dimens.dp_8,
                            onClick = {
                                onAction(EditProfileAction.OnRemoveTempSelectedTopic(it))
                            }
                        )
                    }
            }
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .padding(dimens.dp_16)
            ) {
                Text(
                    text = stringResource(id = R.string.placeholder_count_topic, state.selectedTopic.size.toString()),
                    style = UwangTypography.BodyXS.Regular,
                    color = UwangColors.Text.Secondary
                )
                Text(
                    text = stringResource(id = R.string.label_text_button_reset),
                    style = UwangTypography.BodyXS.Medium,
                    color = UwangColors.State.Primary.Main,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable {
                            onAction(EditProfileAction.OnClearTempSelectedTopic)
                        }
                )
            }
        } else {
            // Add some placeholder image when no one topic selekted
        }
        Divider(color = UwangColors.Text.Border)
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_16))
        Text(
            text = stringResource(id = R.string.title_other_topic),
            style = UwangTypography.BodyXL.SemiBold,
            color = UwangColors.Text.Main,
            modifier = Modifier
                .padding(horizontal = dimens.dp_16)
        )
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_16))
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(dimens.dp_8),
            verticalArrangement = Arrangement.spacedBy(dimens.dp_16),
            modifier = Modifier
                .padding(horizontal = dimens.dp_16)
        ) {
            topicList
                .filter {
                    state.tempSelectedTopic.find { selectedTopic ->
                        selectedTopic == it
                    } == null
                }
                .forEach {
                    RoundedChip(
                        text = it,
                        selected = false,
                        height = dimens.dp_36,
                        verticalPadding = dimens.dp_8,
                        onClick = {
                            onAction(EditProfileAction.OnAddTempSelectedTopic(it))
                        }
                    )
                }
        }
    }
}

@Preview
@Composable
fun EditTopicScreenPreview() {
    UwangTheme {
        EditTopicScreen()
    }
}