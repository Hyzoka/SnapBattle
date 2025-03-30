package com.test.features.components.animate

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.test.core.R
import kotlin.math.roundToInt

@Composable
fun AnimatedCircleIconButton(
    drawableIcon: Int,
    colorIcon: Color,
    colorBackground: Color,
    contentDescription: String?,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
) {
    val buttonOffsetY = remember { Animatable(initialValue = 100f) }
    // Launch the entry animation when the button enters the composition
    LaunchedEffect(Unit) {
        buttonOffsetY.animateTo(
            targetValue = 0f,
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier
            .fillMaxSize()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        CircleIconButton(
            modifier = Modifier
                .size(80.dp)
                .offset { IntOffset(x = 0, y = buttonOffsetY.value.roundToInt()) },
            drawableIcon = drawableIcon,
            colorIcon = colorIcon,
            colorBackground = colorBackground,
            contentDescription = contentDescription,
            iconSize = iconSize,
            onClick = {
                onClick()
            }
        )
    }
}

@Composable
fun CircleIconButton(
    drawableIcon: Int = R.drawable.ic_home_filled_24,
    colorIcon: Color = Color.White,
    colorBackground: Color = Color.Black,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
    modifier: Modifier = Modifier,
    iconSize: Dp = 24.dp,
) {
    Button(
        onClick = onClick,
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = colorBackground,
        ),
        shape = CircleShape,
        modifier = modifier
            .size(45.dp)
            .border(2.dp, Color.White, shape = CircleShape),
    ) {
        Icon(
            modifier = Modifier.size(iconSize),
            imageVector = ImageVector.vectorResource(id = drawableIcon),
            contentDescription = contentDescription,
            tint = colorIcon,
        )
    }
}
