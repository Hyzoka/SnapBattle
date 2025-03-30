package com.test.domain.utils

import kotlin.random.Random

fun generateRandomEndDate(): Long {
    val now = System.currentTimeMillis()
    val isExpired = Random.nextBoolean() // 50% chance que le challenge soit expiré

    return if (isExpired) {
        now - Random.nextLong(3_600_000, 86_400_000) // Entre -1h et -24h
    } else {
        now + Random.nextLong(1_800_000, 172_800_000) // Entre 30 min et 48h
    }
}
