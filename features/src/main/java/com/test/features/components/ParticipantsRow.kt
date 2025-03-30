package com.test.features.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.test.domain.model.user.Participant

@Composable
fun ParticipantsRow(participants: List<Participant>,avatarSize : Int = 48, modifier: Modifier = Modifier) {
    val maxVisible = 3  // 🔥 Nombre max d'avatars affichés avant "+X"
    val remainingCount = participants.size - maxVisible

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        participants.take(maxVisible).forEachIndexed { index, imageUrl ->
            Box(
                modifier = Modifier
                    .offset(x = (-8 * index).dp)
                    .size(avatarSize.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.background, CircleShape)
            ) {
                AsyncImage(
                    model = imageUrl.avatarUrl,
                    contentDescription = "Participant Avatar",
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
        }

        if (remainingCount > 0) {
            Box(
                modifier = Modifier
                    .offset(x = (-8 * maxVisible).dp)
                    .size(avatarSize.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+$remainingCount",
                    color = MaterialTheme.colorScheme.onPrimary,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

