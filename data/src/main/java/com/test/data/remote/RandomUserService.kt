package com.test.data.remote

import com.test.data.model.FakeUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface RandomUserService {
    @GET("api/")
    suspend fun getFakeUsers(@Query("results") results: Int): FakeUserResponse
}
