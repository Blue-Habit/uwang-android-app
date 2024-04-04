/*
 * Copyright © 2023 Blue Habit.
 *
 * Unauthorized copying, publishing of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 */

package com.bluhabit.blu.android.presentation.publicprofile

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
import com.bluhabit.core.ui.components.button.ButtonOutlinedPrimary
import com.bluhabit.core.ui.components.button.ButtonPrimary
import com.bluhabit.core.ui.components.card.ProfileLevelOneNoInfoCard
import com.bluhabit.core.ui.components.card.ProfileLevelWithTopicCard
import com.bluhabit.core.ui.components.topbar.TopAppBarPrimary
import com.bluhabit.core.ui.ext.formatToReadable
import com.bluhabit.core.ui.theme.UwangColors
import com.bluhabit.core.ui.theme.UwangDimens
import com.bluhabit.core.ui.theme.UwangTheme
import com.bluhabit.core.ui.theme.UwangTypography
import kotlinx.coroutines.launch

@Composable
fun PublicProfileScreen(
    state: PublicProfileState = PublicProfileState(),
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
    val scrollState = rememberScrollState()

    BoxWithConstraints(
        modifier = Modifier
            .background(UwangColors.Base.White)
            .safeDrawingPadding()
    ) {
        val screenHeight = maxHeight
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(state = scrollState)
        ) {
            TopAppBarPrimary(
                title = "@" + state.username,
                onBackPressed = {
                    // On back pressed
                },
                action = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_more_vertical),
                        contentDescription = "",
                        tint = UwangColors.Text.Main,
                        modifier = Modifier
                            .size(dimens.dp_24)
                            .clickable {
                                // On menu clicked
                            }
                    )
                }
            )
            Spacer(modifier = Modifier.padding(top = dimens.dp_8))
            TopSection(state = state)
            Spacer(modifier = Modifier.padding(top = dimens.dp_12))
            MiddleSection(state = state)
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
    state: PublicProfileState = PublicProfileState(),
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimens.dp_16)
    ) {
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
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        Text(
            text = state.fullName,
            style = UwangTypography.BodyMedium.Medium,
            color = UwangColors.Text.Main
        )
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        state.bioProfile?.let {
            Text(
                text = it,
                style = UwangTypography.BodyXS.Regular,
                color = UwangColors.Text.Main
            )
        }
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
                text = "Bergabung 21 juli 2023",
                style = UwangTypography.BodyXS.Regular,
                color = UwangColors.Text.Secondary
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 4.dp))
        state.websiteProfile?.let {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_link),
                    contentDescription = stringResource(id = R.string.join_at, state.joinDate),
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
    state: PublicProfileState,
) {
    val ctx = LocalContext.current
    val dimens = UwangDimens.from(ctx)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = dimens.dp_16),
        verticalArrangement = Arrangement.spacedBy(dimens.dp_12)
    ) {
        with(state) {
            when {
                topicList.isNotEmpty() ->
                    ProfileLevelWithTopicCard(
                        topicList = topicList
                    )

                else ->
                    ProfileLevelOneNoInfoCard()
            }
            if (isFollowed)
                ButtonOutlinedPrimary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.dp_36),
                    text = "Mengikuti",
                    borderColor = UwangColors.Text.Border,
                    textColor = UwangColors.Text.Main,
                )
            else
                ButtonPrimary(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(dimens.dp_36),
                    text = "Ikuti"
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
fun PublicProfileScreenPreview() {
    val topicList = stringArrayResource(id = R.array.topic_list).copyOfRange(0,3).toList()
    val dummyState = PublicProfileState(
        username = "johndoe",
        fullName = "John Doe",
        imageProfileUrl = "https://r2.easyimg.io/zvc1zf5s0/profile_default_image.png",
        bioProfile = "Opportunities don't happen. You create them!",
        websiteProfile = "www.johndoe.com",
        topicList = topicList,
        sizePost = 0,
        sizeFollowers = 1244,
        sizeFollowing = 254,
        joinDate = "21 juli 2023",
        isFollowed = false
    )
    UwangTheme {
        PublicProfileScreen(
            state = dummyState
        )
    }
}