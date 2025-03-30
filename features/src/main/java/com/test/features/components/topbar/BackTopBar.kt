package com.test.features.components.topbar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.test.features.components.animate.SlideCountdownTimer

@Composable
fun BackTopBar(
    icon: ImageVector = Icons.Default.ArrowBack,
    iconColor: Color = Color.Black,
    timeOut: Long? = null,
    modifier: Modifier = Modifier,
    onLeftIconClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        // Icône de gauche
        IconButton(
            modifier = Modifier.align(Alignment.CenterStart),
            onClick = { onLeftIconClick() }) {
            Icon(
                imageVector = icon,
                contentDescription = "icon",
                tint = iconColor
            )
        }

        timeOut?.let {
            SlideCountdownTimer(timeOut, modifier = Modifier.align(Alignment.Center))
        }
    }
}
