package com.test.data.di

import com.test.data.remote.FakeApiService
import com.test.data.remote.RandomUserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    @Named("fakeApi")
    fun provideRetrofitFakeApi(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://fakerapi.it/api/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("randomUser")
    fun provideRetrofitRandomUser(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://randomuser.me/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideFakeApiService(
        @Named("fakeApi")
        retrofit: Retrofit
    ): FakeApiService {
        return retrofit.create(FakeApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideRandomUserService(
        @Named("randomUser")
        retrofit: Retrofit
    ): RandomUserService {
        return retrofit.create(RandomUserService::class.java)
    }
}