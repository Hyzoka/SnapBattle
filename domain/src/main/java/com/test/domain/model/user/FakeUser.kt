package com.test.domain.model.user

data class FakeUser(
    val name: Name,
    val picture: Picture
) {
    data class Name(val first: String, val last: String)
    data class Picture(val large: String)
}