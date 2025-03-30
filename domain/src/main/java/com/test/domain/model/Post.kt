package com.test.domain.model

import com.test.domain.model.user.User

data class Post(
    val id: String,
    val imageUrl: String,
    val user: User,
    val likes: Int,
    val comments: Int,
    val shares: Int
)