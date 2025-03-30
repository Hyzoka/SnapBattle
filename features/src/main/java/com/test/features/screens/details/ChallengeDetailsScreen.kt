package com.test.features.screens.details

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.test.core.Colors
import com.test.core.R
import com.test.features.components.animate.AnimatedCircleIconButton
import com.test.features.components.topbar.BackTopBar
import com.test.features.navigation.FAB_EXPLODE_BOUNDS_KEY
import com.test.features.navigation.Screen
import com.test.features.screens.details.discovery.DiscoveryScreen
import com.test.features.screens.details.friends.FollowingScreen
import kotlinx.coroutines.launch

@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedTransitionScope.ChallengeDetailScreen(
    theme: String,
    navController: NavController,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: ChallengeDetailViewModel = hiltViewModel()
) {
    val postsState by viewModel.postsState.collectAsStateWithLifecycle()
    val currentTimestamp = System.currentTimeMillis()
    val pagerState = rememberPagerState(initialPage = 1, pageCount = { postsState.tabItems.size })
    val coroutineScope = rememberCoroutineScope()

    val savedStateHandle = navController.currentBackStackEntry?.savedStateHandle
    val capturedImageUri = savedStateHandle?.get<String>("capturedImageUri")

    LaunchedEffect(capturedImageUri) {
        capturedImageUri?.let { uri ->
            viewModel.addNewPost(uri)
            savedStateHandle.remove<String>("capturedImageUri")
        }
    }

    LaunchedEffect(theme) {
        viewModel.getCurrentTheme(theme)
    }

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        // 🔹 TopBar (Fixé en haut)
        BackTopBar(timeOut = postsState.endAt, onLeftIconClick = {
            navController.popBackStack()
        })

        // 🔹 Tab Bar (Fixée en haut sous le BackTopBar)
        val edge = LocalConfiguration.current.screenWidthDp.dp.div(2).minus(100.dp)
        ScrollableTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            divider = {},
            indicator = { tabPositions ->
                val modifier = Modifier
                    .tabIndicatorOffset(tabPositions[pagerState.currentPage])
                    .padding(horizontal = 38.dp)

                TabRowDefaults.Indicator(
                    modifier, color = Colors.neutral500
                )
            },
            edgePadding = edge
        ) {
            postsState.tabItems.forEachIndexed { index, item ->
                val isSelected = pagerState.currentPage == index
                Tab(
                    selected = isSelected,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = {
                        val textStyle =
                            if (isSelected) MaterialTheme.typography.titleMedium.merge(
                                TextStyle(color = Color.Black)
                            )
                            else TextStyle(
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 17.sp,
                                color = Color.Black.copy(alpha = 0.6f)
                            )
                        Text(text = stringResource(id = item.nameTab), style = textStyle)
                    }
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {

            // 🔹 Contenu avec `HorizontalPager`
            Box(modifier = Modifier.fillMaxSize()) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    when (page) {
                        0 -> FollowingScreen(viewModel)
                        1 -> DiscoveryScreen(viewModel)
                    }
                }
            }
            if (postsState.endAt > currentTimestamp) {
                AnimatedCircleIconButton(
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .sharedBounds(
                            sharedContentState = rememberSharedContentState(
                                key = FAB_EXPLODE_BOUNDS_KEY
                            ),
                            animatedVisibilityScope = animatedVisibilityScope
                        ),
                    drawableIcon = R.drawable.baseline_add_a_photo_24,
                    colorIcon = Color.White,
                    colorBackground = Color.Black,
                    contentDescription = "Open Details",
                    onClick = {
                        navController.navigate(Screen.TakePictureScreen.route)
                    }
                )
            }
        }
    }
}
