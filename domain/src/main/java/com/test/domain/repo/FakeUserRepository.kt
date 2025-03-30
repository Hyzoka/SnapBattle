package com.test.domain.repo

import com.test.domain.utils.ResultState
import com.test.domain.model.user.FakeUser

interface FakeUserRepository {
    suspend fun getUsers(quantity: Int): ResultState<List<FakeUser>>
}
