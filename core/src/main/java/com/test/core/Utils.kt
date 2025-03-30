package com.test.core

fun formatTimeRemaining(timeInMillis: Long): String {
    val seconds = (timeInMillis / 1000) % 60
    val minutes = (timeInMillis / (1000 * 60)) % 60
    val hours = (timeInMillis / (1000 * 60 * 60)) % 24
    val days = (timeInMillis / (1000 * 60 * 60 * 24))

    return when {
        days > 0 -> "$days j $hours h"
        hours > 0 -> "$hours h $minutes min"
        minutes > 0 -> "$minutes min $seconds s"
        else -> "$seconds s"
    }
}
