/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.editprofile.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.bluehabit.core.ui.R
import com.bluhabit.blu.android.presentation.editprofile.EditProfileAction
import com.bluhabit.blu.android.presentation.editprofile.EditProfileState
import com.bluhabit.core.ui.components.alert.AlertError
import com.bluhabit.core.ui.components.alert.AlertSuccess
import com.bluhabit.core.ui.components.chip.RoundedChip
import com.bluhabit.core.ui.components.textfield.TextFieldBox
import com.bluhabit.core.ui.components.textfield.TextFieldPrimary
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography
import java.time.OffsetDateTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun InputEditProfileScreen(
    state: EditProfileState = EditProfileState(),
    onAction: (EditProfileAction) -> Unit = {},
    onBackPressed: () -> Unit = {},
    onPickProfileImageBottomSheetShow: () -> Unit = {},
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)


    val imageProfilePainter = rememberAsyncImagePainter(
        model = when {
            state.imageProfileUrl != null -> state.imageProfileUrl
            else -> "https://r2.easyimg.io/shbq3yqpw/frame_37002.png"
        }
    )
    Box(
        modifier = Modifier
            .background(UwangColors.Base.White)
            .fillMaxSize()
            .safeDrawingPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(UwangColors.Base.White)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(dimens.dp_16)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "",
                    tint = Color(0xFF0A0A0A),
                    modifier = Modifier
                        .clickable(onClick = onBackPressed)
                )
                Text(
                    text = stringResource(id = R.string.edit_profile),
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
                            onAction(EditProfileAction.OnEditProfileSaved)
                        }
                )
            }
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
            Box(
                modifier = Modifier
                    .size(dimens.from(100.dp))
                    .clip(CircleShape)
                    .border(
                        width = dimens.from(1.dp),
                        color = UwangColors.Text.Border,
                        shape = CircleShape
                    )
                    .clickable(onClick = onPickProfileImageBottomSheetShow),
            ) {
                Image(
                    painter = imageProfilePainter,
                    contentDescription = "",
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxSize()
                )
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(UwangColors.Base.Black.copy(0.50f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_pencil),
                        contentDescription = "",
                        tint = UwangColors.Base.White,
                        modifier = Modifier
                            .size(dimens.dp_28)
                    )
                }
            }

            Spacer(modifier = Modifier.padding(bottom = dimens.dp_24))
            if (state.uploadImageProfileErrorAlertVisibility) {
                AlertError(message = stringResource(id = R.string.message_error_upload_image_profile)) {
                    onAction(EditProfileAction.OnUploadProfileImageErrorVisibilityChange(visible = false))
                }
            }
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
            TextFieldPrimary(
                modifier = Modifier
                    .fillMaxWidth(),
                label = stringResource(id = R.string.label_field_username),
                placeholder = stringResource(id = R.string.placeholder_field_username),
                value = state.username,
                onValueChange = {
                    onAction(EditProfileAction.OnUsernameChange(it))
                },
                state = state.fullNameState,
                leadingIcon = {
                    Image(painter = painterResource(id = R.drawable.ic_at_sign), contentDescription = "")
                },
                onClickTrailingIcon = {
                    onAction(EditProfileAction.OnUsernameChange(""))
                }
            )
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
            TextFieldPrimary(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.sign_up_screen_input_full_name_label),
                placeholder = stringResource(id = R.string.sign_up_screen_input_full_name_placeholder),
                value = state.fullName,
                onValueChange = {
                    onAction(EditProfileAction.OnFullNameChange(it))
                },
                onClickTrailingIcon = {
                    onAction(EditProfileAction.OnFullNameChange(""))
                },
                state = state.fullNameState,
            )
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
            TextFieldPrimary(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(id = R.string.label_field_link),
                placeholder = stringResource(id = R.string.placeholder_field_link),
                value = state.link,
                onValueChange = {
                    onAction(EditProfileAction.OnLinkChange(it))
                },
                onClickTrailingIcon = {
                    onAction(EditProfileAction.OnLinkChange(""))
                },
                state = state.linkState,
            )
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
            TextFieldBox(
                modifier = Modifier
                    .heightIn(dimens.from(100.dp))
                    .fillMaxWidth(),
                label = stringResource(id = R.string.label_field_bio),
                placeholder = stringResource(id = R.string.placeholder_field_bio),
                value = state.bio,
                onValueChange = {
                    onAction(EditProfileAction.OnBioChange(it))
                },
                state = state.bioState,
            )
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.label_selected_topic),
                    style = UwangTypography.BodyMedium.Medium,
                    color = UwangColors.Text.Main,
                )
                Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(dimens.dp_8),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    state.selectedTopic
                        .forEach {
                            RoundedChip(
                                text = it,
                                selected = true,
                                height = dimens.dp_36,
                                verticalPadding = dimens.dp_8,
                                onClick = {
                                    onAction(EditProfileAction.OnRemoveSelectedTopic(it))
                                }
                            )
                        }
                }
                Spacer(modifier = Modifier.padding(bottom = dimens.dp_8))
                Row(
                    horizontalArrangement = Arrangement.spacedBy(dimens.from(4.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            onAction(EditProfileAction.OnEditNewTopic)
                        }
                        .padding(
                            horizontal = dimens.dp_12,
                            vertical = dimens.dp_8
                        )
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = "",
                        tint = UwangColors.State.Primary.Main,
                        modifier = Modifier
                            .size(dimens.dp_20)
                    )
                    Text(
                        text = stringResource(id = R.string.add_topic),
                        style = UwangTypography.BodySmall.Medium,
                        color = UwangColors.State.Primary.Main
                    )
                }
            }
        }
        if (state.uploadImageProfileSuccessAlertVisibility) {
            AlertSuccess(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(
                        bottom = dimens.from(40.dp),
                        start = dimens.dp_16,
                        end = dimens.dp_16
                    ),
                message = stringResource(id = R.string.message_success_upload_image_profile)
            ) {
                onAction(EditProfileAction.OnUploadProfileImageSuccessVisibilityChange(visible = false))
            }
        }
    }


}

@Preview
@Composable
fun InputEditProfileScreenPreview() {
    val currentTime = OffsetDateTime.now().toString()
    UwangTheme {
        val topicList = stringArrayResource(id = R.array.topic_list)
        val dummyState = EditProfileState(
            imageProfileUrl = "https://static.remove.bg/sample-gallery/graphics/bird-thumbnail.jpg",
            selectedTopic = listOf(
                topicList[0],
                topicList[1],
                topicList[2],
            ),
        )
        InputEditProfileScreen(
            state = dummyState,
        )
    }
}