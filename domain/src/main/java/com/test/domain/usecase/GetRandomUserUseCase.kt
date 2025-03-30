package com.test.domain.usecase

import com.test.domain.model.user.FakeUser
import com.test.domain.repo.FakeUserRepository
import com.test.domain.utils.ResultState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetRandomUserUseCase @Inject constructor(
    private val userRepository: FakeUserRepository
) {
    fun execute(): Flow<ResultState<FakeUser>> = flow {
        emit(ResultState.Loading)
        val result = userRepository.getUsers(1)
        when (result) {
            is ResultState.Success -> emit(ResultState.Success(result.data.first()))
            is ResultState.Error -> emit(ResultState.Error(result.message))
            is ResultState.Loading -> emit(ResultState.Loading)
        }
    }.flowOn(Dispatchers.IO)
}
