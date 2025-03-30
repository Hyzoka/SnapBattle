package com.test.features.components.animate

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.with
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import com.test.core.formatTimeRemaining

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedCountdown(remainingTime: Long) {
    val formattedTime = remember(remainingTime) { formatTimeRemaining(remainingTime) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        AnimatedContent(
            targetState = formattedTime,
            transitionSpec = {
                slideInVertically { height -> height } + fadeIn() with
                        slideOutVertically { height -> -height } + fadeOut()
            }
        ) { time ->
            Text(
                text = time,
                color = Color.White,
                style = MaterialTheme.typography.bodyLarge,
                maxLines = 1,
            )
        }
    }
}
