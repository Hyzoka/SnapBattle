package com.test.features.screens.details.discovery

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.test.features.components.MessageView
import com.test.features.components.card.PostCard
import com.test.features.components.loading.LoadingListPostSkeleton
import com.test.features.screens.details.ChallengeDetailViewModel

@Composable
fun DiscoveryScreen(
    viewModel: ChallengeDetailViewModel
) {
    val postsState by viewModel.postsState.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()) {
        when {
            postsState.isLoading -> LoadingListPostSkeleton(modifier = Modifier)
            postsState.error != null -> {
                //TODO error content
            }

            postsState.posts.isEmpty() -> {
                //TODO add lottie
            }

            else -> LazyColumn(contentPadding = PaddingValues(top = 16.dp,bottom = 100.dp)) {
                //add our post
                postsState.ourPost?.let {
                    item {
                        PostCard(post = postsState.ourPost!!)
                    }
                }
                items(postsState.posts) { post ->
                    PostCard(post = post)
                }
            }
        }
    }
}