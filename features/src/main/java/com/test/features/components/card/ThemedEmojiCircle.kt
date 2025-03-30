package com.test.features.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ThemedEmojiCircle(emoji: String, modifier: Modifier = Modifier) {
    // Liste de paires de couleurs pour le gradient
    val gradientColors = listOf(
        listOf(Color(0xFFFFA726), Color(0xFFFF7043)), // Orange -> Rouge
        listOf(Color(0xFFEC407A), Color(0xFFFFA726)), // Rose -> Orange
        listOf(Color(0xFF66BB6A), Color(0xFF42A5F5)), // Vert -> Bleu
        listOf(Color(0xFF42A5F5), Color(0xFFAB47BC)), // Bleu -> Violet
        listOf(Color(0xFFAB47BC), Color(0xFFEC407A))  // Violet -> Rose
    )

    // Liste de directions pour le gradient
    val gradientDirections = listOf(
        Pair(Offset(0f, 0f), Offset(100f, 100f)), // Haut-gauche → Bas-droit
        Pair(Offset(100f, 0f), Offset(0f, 100f)), // Haut-droit → Bas-gauche
        Pair(Offset(0f, 100f), Offset(100f, 0f)), // Bas-gauche → Haut-droit
        Pair(Offset(100f, 100f), Offset(0f, 0f))  // Bas-droit → Haut-gauche
    )

    // Sélection aléatoire d'une paire de couleurs et d'une direction
    val selectedColors = remember { gradientColors.random() }
    val direction = remember { gradientDirections.random() }

    val backgroundBrush = Brush.linearGradient(
        colors = selectedColors,
        start = direction.first,
        end = direction.second
    )

    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(72.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape) // Bordure blanche
            .background(backgroundBrush)
    ) {
        Text(
            text = emoji,
            fontSize = 32.sp,
            textAlign = TextAlign.Center
        )
    }
}

