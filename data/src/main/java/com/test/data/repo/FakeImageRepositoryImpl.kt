package com.test.data.repo

import com.test.data.remote.FakeApiService
import com.test.domain.utils.ResultState
import com.test.domain.model.event.FakeImage
import com.test.domain.repo.FakeImageRepository
import javax.inject.Inject

class FakeImageRepositoryImpl @Inject constructor(private val api: FakeApiService) :
    FakeImageRepository {

    override suspend fun getImageByTheme(quantity: Int, type : String): ResultState<List<FakeImage>> {
        return try {
            val response = api.getFakeImages(quantity = quantity,type = type)
            ResultState.Success(response.data)
        } catch (e: Exception) {
            ResultState.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}
