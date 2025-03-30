package com.test.data.repo

import com.test.data.remote.RandomUserService
import com.test.domain.utils.ResultState
import com.test.domain.model.user.FakeUser
import com.test.domain.repo.FakeUserRepository
import javax.inject.Inject

class FakeUserRepositoryImpl @Inject constructor(private val api: RandomUserService) :
    FakeUserRepository {

    override suspend fun getUsers(quantity: Int): ResultState<List<FakeUser>> {
        return try {
            val response = api.getFakeUsers(quantity)
            ResultState.Success(response.results)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
