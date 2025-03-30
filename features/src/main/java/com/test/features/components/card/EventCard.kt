package com.test.features.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.test.core.Colors
import com.test.core.ContentCardType
import com.test.core.R
import com.test.domain.model.event.Event
import com.test.features.components.ParticipantsRow


@Composable
fun EventCard(event: Event, onClickOnCard: ((Event) -> Unit)? = null) {
    val currentTime by rememberUpdatedState(System.currentTimeMillis())
    val remainingTime = event.endDate - currentTime
    val isExpired = remainingTime <= 0


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(ContentCardType.EVENT.cardHeight.dp)
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable {
                onClickOnCard?.invoke(event)
            },
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),

        ) {
        val contentPadding = 16.dp

        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.TopStart,
        ) {
            AsyncImage(
                model = event.imageUrl,
                placeholder = painterResource(id = R.drawable.ic_image_placeholder),
                error = painterResource(id = R.drawable.ic_image_placeholder),
                contentDescription = event.imageUrl,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(ContentCardType.EVENT.thumbnailHeight.dp),
                contentScale = ContentScale.Crop,
            )
            Box(
                modifier = Modifier
                    .background(
                        Brush.verticalGradient(
                            colorStops = arrayOf(
                                0F to Color.Transparent,
                                0.5F to Color.Transparent,
                                1F to Color.White,
                            ),
                        ),
                    )
                    .fillMaxWidth()
                    .height(ContentCardType.EVENT.thumbnailHeight.dp),
            )
            Box(modifier = Modifier.padding(contentPadding)) {
                if (isExpired) {
                    Text(
                        text = stringResource(id = R.string.challenge_expired),
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier
                            .background(
                                color = Color.Black.copy(0.3f),
                                shape = RoundedCornerShape(4.dp),
                            )
                            .padding(8.dp),
                        maxLines = 2,
                        color = Color.White,
                    )

                } else {
                    Text(
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .background(
                                Colors.success,
                                RoundedCornerShape(8.dp),
                            )
                            .wrapContentWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        text = stringResource(
                            id = R.string.in_progress,
                        ),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                    )
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomEnd),
                verticalAlignment = Alignment.Bottom
            ) {
                Column(
                    modifier = Modifier
                        .padding(contentPadding),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.Bottom),
                    horizontalAlignment = Alignment.CenterHorizontally,

                    ) {
                    ThemedEmojiCircle(event.emoji)

                    Text(
                        text = event.title.uppercase(),
                        textAlign = TextAlign.Start,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        style = MaterialTheme.typography.labelMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = true,
                    )
                }
                Spacer(Modifier.weight(1f))
                ParticipantsRow(
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    avatarSize = 34,
                    participants = event.participants,
                )
            }
        }
    }
}