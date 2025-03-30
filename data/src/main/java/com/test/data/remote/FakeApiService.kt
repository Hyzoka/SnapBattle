package com.test.data.remote

import com.test.data.model.FakeImageResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface FakeApiService {
    @GET("images")
    suspend fun getFakeImages(
        @Query("_quantity") quantity: Int,
        @Query("_type") type: String = "any",
        @Query("_width") with: String = "390",
        @Query("_height") height: String = "520",
    ): FakeImageResponse
}