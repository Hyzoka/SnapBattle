package com.test.domain.model.event

import com.test.domain.model.user.Participant

data class Event(
    val title: String,
    val emoji: String,
    val description: String,
    val imageUrl: String,
    val participants: List<Participant>,
    val endDate: Long // en millisecondes

)