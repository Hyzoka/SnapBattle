package com.test.domain.usecase

import com.test.domain.model.event.Event
import com.test.domain.model.user.Participant
import com.test.domain.repo.FakeImageRepository
import com.test.domain.repo.FakeUserRepository
import com.test.domain.utils.ResultState
import com.test.domain.utils.generateRandomEndDate
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withTimeoutOrNull
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val imageRepository: FakeImageRepository,
    private val userRepository: FakeUserRepository
) {
    fun execute(): Flow<ResultState<List<Event>>> = flow {
        emit(ResultState.Loading)
        try {
            coroutineScope {
                val themes = mapOf(
                    "Pokémon" to "🦊",
                    "Cow-boy" to "🤠",
                    "Asie" to "🍜"
                )

                val title = listOf("Pokémon", "Cow-boy", "Asie")
                // Récupération des images pour chaque thème
                val imagesDeferred = themes.keys.map { theme ->
                    async { imageRepository.getImageByTheme(1, theme) } // 1 image par thème
                }

                val challenges = imagesDeferred.mapIndexed { index, deferredImageResult ->
                    val imageResult = deferredImageResult.await()

                    if (imageResult is ResultState.Success && imageResult.data.isNotEmpty()) {
                        val image = imageResult.data.first()

                        async {
                            val usersResult = withTimeoutOrNull(3000) {
                                userRepository.getUsers((3..6).random())
                            } ?: ResultState.Error("Timeout")

                            val participants = if (usersResult is ResultState.Success) {
                                usersResult.data.map { user ->
                                    Participant(
                                        name = "${user.name.first} ${user.name.last}",
                                        avatarUrl = user.picture.large
                                    )
                                }
                            } else {
                                emptyList()
                            }

                            // Création de l'événement avec l'emoji associé
                            Event(
                                title = title[index], // on triche sur les datas
                                description = image.description,
                                imageUrl = image.url,
                                participants = participants,
                                endDate = generateRandomEndDate(),
                                emoji = themes.values.elementAtOrNull(index)
                                    ?: "🎉"
                            )
                        }
                    } else {
                        null
                    }
                }.filterNotNull().awaitAll()

                emit(ResultState.Success(challenges))
            }
        } catch (e: Exception) {
            emit(ResultState.Error(e.localizedMessage ?: "Unexpected Error"))
        }
    }.flowOn(Dispatchers.IO)
}
