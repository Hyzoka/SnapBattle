package com.test.core

enum class ContentCardType(
    val cardWidth: Int,
    val cardHeight: Int,
    val thumbnailHeight: Int,
    val padding: Int,
) {
    EVENT( 300, 270, 175, 16),
    POST( 300, 520, 175, 16),

}