package com.test.features.screens.home

import com.test.domain.model.event.Event


data class HomeChallengeState(
    val isLoading: Boolean = true,
    val events: List<Event> = emptyList(),
    val error: String? = null,
    val searchText: String = ""
)