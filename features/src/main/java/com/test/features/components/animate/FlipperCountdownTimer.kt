package com.test.features.components.animate

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

@Composable
fun SlideCountdownTimer(
    targetTimeMillis: Long,
    textStyle: TextStyle = MaterialTheme.typography.titleLarge,
    modifier: Modifier = Modifier
) {
    var timeRemaining by remember { mutableStateOf(calculateTimeRemaining(targetTimeMillis)) }

    // Update the time remaining every second
    LaunchedEffect(Unit) {
        while (timeRemaining > 0) {
            delay(1000)
            timeRemaining = calculateTimeRemaining(targetTimeMillis)
        }
    }

    // Animated countdown display
    SlideCountdownDisplay(timeRemaining, textStyle, modifier)
}

@Composable
fun SlideCountdownDisplay(
    timeRemaining: Long,
    textStyle: TextStyle,
    modifier: Modifier = Modifier
) {
    val hours = (timeRemaining / (1000 * 60 * 60)) % 24
    val minutes = (timeRemaining / (1000 * 60)) % 60
    val seconds = (timeRemaining / 1000) % 60

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        SlideNumber(number = hours, textStyle)
        Text(":", style = textStyle, fontWeight = FontWeight.Bold)
        SlideNumber(number = minutes, textStyle)
        Text(":", style = textStyle, fontWeight = FontWeight.Bold)
        SlideNumber(number = seconds, textStyle)
    }
}

@Composable
fun SlideNumber(number: Long, textStyle: TextStyle) {
    var currentNumber by remember { mutableLongStateOf(number) }
    var nextNumber by remember { mutableLongStateOf(number) }

    // Trigger animation when the number changes
    LaunchedEffect(number) {
        nextNumber = number
    }

    val offsetY = remember { Animatable(initialValue = 0f) }

    LaunchedEffect(nextNumber) {
        if (currentNumber != nextNumber) {
            offsetY.animateTo(
                targetValue = -50f,
                animationSpec = tween(durationMillis = 500)
            )
            currentNumber = nextNumber
            offsetY.snapTo(0f)
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(50.dp)
    ) {
        // Current number (sliding up)
        Text(
            text = "%02d".format(currentNumber),
            style = textStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt()) }
                .alpha(1f - offsetY.value.absoluteValue / 50f)
        )
        // Next number (sliding in)
        Text(
            text = "%02d".format(nextNumber),
            style = textStyle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .offset { IntOffset(x = 0, y = offsetY.value.roundToInt() + 50) }
                .alpha(offsetY.value.absoluteValue / 50f)
        )
    }
}

fun calculateTimeRemaining(targetTimeMillis: Long): Long {
    val currentTimeMillis = System.currentTimeMillis()
    return if (targetTimeMillis > currentTimeMillis) {
        targetTimeMillis - currentTimeMillis
    } else {
        0
    }
}
