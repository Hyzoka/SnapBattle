package com.test.domain.repo

import com.test.domain.utils.ResultState
import com.test.domain.model.event.FakeImage

interface FakeImageRepository {
    suspend fun getImageByTheme(quantity: Int, type : String): ResultState<List<FakeImage>>
}
