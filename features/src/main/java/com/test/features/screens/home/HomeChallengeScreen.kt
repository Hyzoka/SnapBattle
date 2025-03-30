package com.test.features.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.test.features.components.MessageView
import com.test.features.components.card.EventCard
import com.test.features.components.loading.LoadingListEventSkeleton
import com.test.features.components.topbar.TopBar
import com.test.features.navigation.Screen

@Composable
fun HomeChallengeScreen(
    navController: NavController, viewModel: HomeChallengeViewModel = hiltViewModel()
) {

    val challengesState by viewModel.viewState.collectAsStateWithLifecycle()

    Column {
        TopBar(onLeftIconClick = {
            navController.navigate(Screen.ProfileScreen.route)

        }, onRightIconClick = {
            navController.navigate(Screen.NotificationScreen.route)
        })
        when {
            challengesState.isLoading -> LoadingListEventSkeleton(modifier = Modifier)
            challengesState.error != null -> {
                //TODO error content
            }

            challengesState.events.isEmpty() -> {
                //TODO add lottie
            }

            else -> LazyColumn {
                items(challengesState.events) { challenge ->
                    EventCard(event = challenge, onClickOnCard = {
                        navController.navigate(Screen.ChallengeDetailsScreen.route + "/${challenge.title}")
                    })
                }
            }
        }
    }
}