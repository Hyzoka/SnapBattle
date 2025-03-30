package com.test.data.di

import com.test.data.repo.FakeImageRepositoryImpl
import com.test.data.repo.FakeUserRepositoryImpl
import com.test.domain.repo.FakeImageRepository
import com.test.domain.repo.FakeUserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindFakeImageRepository(
        impl: FakeImageRepositoryImpl
    ): FakeImageRepository

    @Binds
    @Singleton
    abstract fun bindFakeUserRepositoryImpl(
        impl: FakeUserRepositoryImpl
    ): FakeUserRepository
}

