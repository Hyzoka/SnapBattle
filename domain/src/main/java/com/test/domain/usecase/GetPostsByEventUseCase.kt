package com.test.domain.usecase

import com.android.identity.util.UUID
import com.test.domain.model.Post
import com.test.domain.model.user.User
import com.test.domain.repo.FakeImageRepository
import com.test.domain.repo.FakeUserRepository
import com.test.domain.utils.ResultState
import com.test.domain.utils.onError
import com.test.domain.utils.onSuccess
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetPostsByEventUseCase @Inject constructor(
    private val userRepository: FakeUserRepository,
    private val imageRepository: FakeImageRepository,
) {
    fun execute(theme: String): Flow<ResultState<List<Post>>> = flow {
        emit(ResultState.Loading)
        try {
            coroutineScope {
                val usersResult = async { userRepository.getUsers((5..10).random()) }.await()

                if (usersResult is ResultState.Success) {
                    // Get the list of users
                    val users = usersResult.data

                    // Fetch images for the posts
                    val imageResponse = async {
                        imageRepository.getImageByTheme(
                            quantity = users.size,
                            type = theme,
                        )
                    }.await()

                    // Map users to posts with images
                    imageResponse.onSuccess { data ->

                        val posts = users.zip(data) { user, imageData ->
                            Post(
                                id = UUID.randomUUID().toString(),
                                imageUrl = imageData.url,
                                user = User(
                                    id = UUID.randomUUID().toString(),
                                    name = "${user.name.first} ${user.name.last}",
                                    avatarUrl = user.picture.large
                                ),
                                likes = (10..500).random(),
                                comments = (0..100).random(),
                                shares = (0..50).random()
                            )
                        }
                        emit(ResultState.Success(posts))
                    }
                    imageResponse.onError {
                        emit(ResultState.Error("Erreur lors de la récupération des posts"))
                    }
                } else {
                    emit(ResultState.Error("Erreur lors de la récupération des utilisateurs"))
                }
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Erreur inattendue"))
        }
    }.flowOn(Dispatchers.IO)
}
