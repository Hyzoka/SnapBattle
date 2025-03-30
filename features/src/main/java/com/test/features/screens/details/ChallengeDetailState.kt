package com.test.features.screens.details

import com.test.core.R
import com.test.domain.model.Post
import com.test.domain.utils.generateRandomEndDate

data class ChallengeDetailState(
    val isLoading: Boolean = true,
    val theme: String = "",
    val endAt: Long = generateRandomEndDate(), // on suppose qu'on récupére les bonnes donnée api
    val posts: List<Post> = emptyList(),
    val ourPost: Post? = null,
    val error: String? = null,
    val tabItems: ArrayList<ChallengeDetailsTabs> = arrayListOf(
        ChallengeDetailsTabs.DISCOVERY,
        ChallengeDetailsTabs.FRIENDS
    )
)

enum class ChallengeDetailsTabs(val nameTab: Int) {
    FRIENDS(R.string.friends),
    DISCOVERY(R.string.discovery)
}