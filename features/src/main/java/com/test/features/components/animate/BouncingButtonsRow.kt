package com.test.features.components.animate

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.test.core.R
import kotlin.math.roundToInt

@Composable
fun AnimatedCircleIconButtonsRow(
    modifier: Modifier = Modifier,
    onCloseClick: () -> Unit,
    onCheckClick: () -> Unit
) {
    val buttonOffsetX = remember { Animatable(0f) } // Offset initial à 0 (superposés)

    // Lancer l'animation dès l'affichage
    LaunchedEffect(Unit) {
        buttonOffsetX.animateTo(
            targetValue = 150f, // Distance vers laquelle chaque bouton va se déplacer
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 24.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircleIconButton(
                modifier = Modifier
                    .size(62.dp)
                    .offset {
                        IntOffset(
                            (-buttonOffsetX.value).roundToInt(),
                            0
                        )
                    }, // Déplacement vers la gauche
                drawableIcon = R.drawable.baseline_close_24,
                colorIcon = Color.Black,
                colorBackground = Color.White,
                contentDescription = "Close",
                onClick = onCloseClick
            )

            Spacer(modifier = Modifier.width(16.dp)) // Espace entre les boutons

            CircleIconButton(
                modifier = Modifier
                    .size(62.dp)
                    .offset {
                        IntOffset(
                            buttonOffsetX.value.roundToInt(),
                            0
                        )
                    }, // Déplacement vers la droite
                drawableIcon = R.drawable.baseline_check_24,
                colorIcon = Color.Black,
                colorBackground = Color.White,
                contentDescription = "Check",
                onClick = onCheckClick
            )
        }
    }
}
