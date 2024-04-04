/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.bluehabit.core.ui.R
import com.bluhabit.blu.android.presentation.home.HomeAction
import com.bluhabit.blu.android.presentation.home.HomeState
import com.bluhabit.core.ui.components.button.ButtonOutlinedPrimary
import com.bluhabit.core.ui.components.card.CompleteProfileCard
import com.bluhabit.core.ui.components.card.ProfileLevelOneCard
import com.bluhabit.core.ui.components.card.ProfileLevelOneNoInfoCard
import com.bluhabit.core.ui.components.card.ProfileTopicCard
import com.bluhabit.core.ui.ext.formatToReadable
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    paddingValues: PaddingValues = PaddingValues(),
    state: HomeState = HomeState(),
    onAction: (HomeAction) -> Unit = {},
    onEditProfile: () -> Unit = {}
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    val tabList = listOf(
        stringResource(id = R.string.tab_post),
        stringResource(id = R.string.tab_reply),
        stringResource(id = R.string.tab_repost),
    )
    val pagerState = rememberPagerState(0) {
        tabList.size
    }
    val middlePagerState = rememberPagerState(0) {
        2
    }
    val scrollState = rememberScrollState()

    LaunchedEffect(Unit) {
        onAction(HomeAction.GetProfile)
    }

    BoxWithConstraints(
        modifier = Modifier
            .background(UwangColors.Base.White)
            .safeDrawingPadding()
            .padding(paddingValues)
    ) {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            TopSection(state = state)
            Spacer(modifier = Modifier.padding(top = dimens.dp_12))
            MiddleSection(state = state, pagerState = middlePagerState, onEditProfile)
            Spacer(modifier = Modifier.padding(top = dimens.dp_16))
            BottomSection(
                screenHeight = screenHeight,
                pagerState = pagerState,
                tabList = tabList,
                scrollState = scrollState
            )

        }
    }
}

@Composable
fun TopSection(
    state: HomeState = HomeState(),
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimens.dp_16)
    ) {
        if (state.username.isNotEmpty()) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = dimens.dp_8)
            ) {
                Text(
                    text = "@" + state.username,
                    color = UwangColors.Text.Main,
                    style = UwangTypography.BodyLarge.SemiBold,
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_menu),
                    contentDescription = "",
                    modifier = Modifier
                        .clickable {
                            // On Menu Pressed
                        }
                        .padding(
                            horizontal = dimens.from(3.dp),
                            vertical = dimens.from(6.dp)
                        )
                )
            }
        }
        Spacer(modifier = Modifier.padding(bottom = dimens.dp_8))
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                model = state.imageProfileUrl ?: "https://r2.easyimg.io/shbq3yqpw/frame_37002.png",
                contentDescription = "",
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .border(width = dimens.from(1.dp), color = UwangColors.Text.Border, shape = CircleShape)
                    .padding(dimens.from(4.dp))
                    .size(dimens.from(80.dp))
                    .clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(dimens.from(66.7.dp))
                    .height(dimens.from(42.dp))
            ) {
                Text(
                    text = state.sizePost.formatToReadable(),
                    style = UwangTypography.BodyMedium.SemiBold,
                    color = UwangColors.Text.Main
                )
                Text(
                    text = stringResource(id = R.string.post),
                    style = UwangTypography.BodyXS.Regular,
                    color = UwangColors.Text.Secondary
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(dimens.from(66.7.dp))
                    .height(dimens.from(42.dp))
            ) {
                Text(
                    text = state.sizeFollowers.formatToReadable(),
                    style = UwangTypography.BodyMedium.SemiBold,
                    color = UwangColors.Text.Main
                )
                Text(
                    text = stringResource(id = R.string.followers),
                    style = UwangTypography.BodyXS.Regular,
                    color = UwangColors.Text.Secondary
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .width(dimens.from(66.7.dp))
                    .height(dimens.from(42.dp))
            ) {
                Text(
                    text = state.sizeFollowing.formatToReadable(),
                    style = UwangTypography.BodyMedium.SemiBold,
                    color = UwangColors.Text.Main
                )
                Text(
                    text = stringResource(id = R.string.following),
                    style = UwangTypography.BodyXS.Regular,
                    color = UwangColors.Text.Secondary
                )
            }
        }
        state.fullName?.let {
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            Text(
                text = it,
                style = UwangTypography.BodyMedium.Medium,
                color = UwangColors.Text.Main
            )
        }
        state.bioProfile?.let {
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            Text(
                text = it,
                style = UwangTypography.BodyXS.Regular,
                color = UwangColors.Text.Main
            )
        }
        state.joinDate?.let {
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_calendar),
                    contentDescription = "",
                    tint = UwangColors.Text.Secondary
                )
                Text(
                    text = it,
                    style = UwangTypography.BodyXS.Regular,
                    color = UwangColors.Text.Secondary
                )
            }
        }
        state.websiteProfile?.let {
            Spacer(modifier = Modifier.padding(bottom = 4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = "",
                    tint = UwangColors.State.Primary.Main
                )
                Text(
                    text = it,
                    style = UwangTypography.BodyXS.Regular,
                    color = UwangColors.State.Primary.Main
                )
            }
        }
    }
}

@Composable
fun MiddleSection(
    state: HomeState,
    pagerState: PagerState,
    onEditProfile: () -> Unit = {},
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        with(state) {
            when {
                completedStep == 4 ->
                    HorizontalPager(
                        state = pagerState,
                        contentPadding = PaddingValues(horizontal = dimens.dp_16),
                        pageSpacing = dimens.dp_8,
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        when (it) {
                            0 -> ProfileLevelOneCard(
                                currentPoint = currentPoint,
                                sizePoint = sizePoint
                            ) {
                                // On Card Click
                            }

                            1 -> ProfileTopicCard(
                                topicList = topicList,
                                onTopicChipClicked = {
                                    // On Topic Clicked
                                },
                            ) {
                                // On Card Click
                            }
                        }
                    }

                currentPoint > 0 ->
                    ProfileLevelOneCard(
                        currentPoint = currentPoint,
                        sizePoint = sizePoint,
                        modifier = Modifier.padding(horizontal = dimens.dp_16)
                    ) {
                        // On Card Click
                    }

                else ->
                    ProfileLevelOneNoInfoCard(
                        modifier = Modifier
                            .padding(horizontal = dimens.dp_16)
                    )
            }
            if (completedStep < 4) {
                Spacer(modifier = Modifier.padding(bottom = dimens.from(8.dp)))
                CompleteProfileCard(
                    modifier = Modifier
                        .padding(horizontal = dimens.dp_16),
                    completedStep = completedStep,
                    sizeStep = sizeStep,
                )
            }
            Spacer(modifier = Modifier.padding(bottom = dimens.dp_12))
            ButtonOutlinedPrimary(
                modifier = Modifier
                    .padding(horizontal = dimens.dp_16)
                    .fillMaxWidth()
                    .height(dimens.dp_36),
                text = stringResource(id = R.string.label_button_edit_profile),
                borderColor = UwangColors.Text.Border,
                textColor = UwangColors.Text.Main,
                onClick = onEditProfile
            )
        }
    }
}

@Composable
fun BottomSection(
    screenHeight: Dp,
    pagerState: PagerState,
    tabList: List<String>,
    scrollState: ScrollState
) {
    val scope = rememberCoroutineScope()
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    val indicatorPadding: PaddingValues = when {
        (pagerState.currentPage == 0) -> PaddingValues(start = dimens.dp_16)
        (pagerState.currentPage == pagerState.pageCount - 1) -> PaddingValues(end = dimens.dp_16)
        else -> PaddingValues()
    }

    Column(
        modifier = Modifier
            .height(screenHeight)
    ) {
        TabRow(
            modifier = Modifier
                .height(dimens.from(44.dp)),
            selectedTabIndex = pagerState.currentPage,
            indicator = { tabPositions: List<TabPosition> ->
                Box(
                    modifier = Modifier
                        .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                        .height(dimens.from(4.dp))
                        .padding(indicatorPadding)
                        .clip(RoundedCornerShape(dimens.dp_16))
                        .background(UwangColors.State.Primary.Main)
                )
            },
            backgroundColor = UwangColors.Base.White
        ) {
            tabList.forEachIndexed { index, title ->
                Tab(
                    modifier = Modifier
                        .padding(
                            when (index) {
                                0 -> PaddingValues(start = dimens.dp_16)
                                (tabList.size - 1) -> PaddingValues(end = dimens.dp_16)
                                else -> PaddingValues()
                            }
                        ),
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                ) {
                    Text(
                        text = title,
                        style = UwangTypography.LabelMedium.Medium,
                        color = UwangColors.Text.Main
                    )
                }
            }
        }
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxHeight()
                .nestedScroll(remember {
                    object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            return if (available.y > 0) Offset.Zero else Offset(
                                x = 0f,
                                y = -scrollState.dispatchRawDelta(-available.y)
                            )
                        }
                    }
                }),
            verticalAlignment = Alignment.Top
        ) { page ->
            Page(page)
        }
    }
}

@Composable
fun Page(
    page: Int
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(dimens.dp_12)
    ) {
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = dimens.dp_28),
                verticalArrangement = Arrangement.spacedBy(dimens.dp_8),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(id = R.drawable.empty_post_image),
                    contentDescription = "",
                    modifier = Modifier
                        .width(dimens.from(226.dp))
                        .height(dimens.from(182.4.dp))
                )
                Text(
                    text = stringResource(id = R.string.empty_post),
                    style = UwangTypography.BodySmall.Medium,
                    color = UwangColors.Text.Secondary
                )
            }
        }
    }
}

@Preview
@Composable
fun ProfileScreenPreview() {
    val topicList = stringArrayResource(id = R.array.topic_list).copyOfRange(0, 3).toList()
    val dummyState = HomeState(
        username = "johndoe",
        fullName = "John Doe",
        imageProfileUrl = null,
        bioProfile = null,
        websiteProfile = null,
        currentPoint = 100,
        sizePoint = 500,
        topicList = topicList,
        completedStep = 1,
        sizeStep = 4,
        sizePost = 0,
        sizeFollowers = 1244,
        sizeFollowing = 254,
        joinDate = "21 juli 2023"
    )
    UwangTheme {
        ProfileScreen(
            state = dummyState
        )
    }
}