package com.test.features.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.model.Post
import com.test.domain.model.user.User
import com.test.domain.usecase.GetPostsByEventUseCase
import com.test.domain.usecase.GetRandomUserUseCase
import com.test.domain.utils.ResultState
import com.test.domain.utils.onLoading
import com.test.domain.utils.onSuccess
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChallengeDetailViewModel @Inject constructor(
    private val getPostsByEventUseCase: GetPostsByEventUseCase,
    private val getRandomUserUseCase: GetRandomUserUseCase,
) : ViewModel() {
    private val _postsState = MutableStateFlow(ChallengeDetailState())
    val postsState: StateFlow<ChallengeDetailState> = _postsState


    fun getCurrentTheme(theme: String) {
        viewModelScope.launch {
            getPostsByEventUseCase.execute(theme).collect { result ->
                result.onLoading {
                    _postsState.update { it.copy(isLoading = true) }
                }.onSuccess { postsFromApi ->
                    _postsState.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            posts = (currentState.posts + postsFromApi).distinctBy { it.user }
                                .sortedBy { it.id }
                        )
                    }
                }
            }
        }
    }


    fun addNewPost(imageUri: String) {
        viewModelScope.launch {
            getRandomUserUseCase.execute()
                .collect { result ->
                    when (result) {
                        is ResultState.Success -> {
                            val newPost = Post(
                                id = "1",
                                imageUrl = imageUri,
                                user = User(
                                    id = "1",
                                    name = result.data.name.first,
                                    avatarUrl = result.data.picture.large
                                ),
                                likes = (10..500).random(),
                                comments = (5..50).random(),
                                shares = (1..20).random()
                            )
                            Log.d(
                                "ChallengeDetailScreen ChallengeDetailViewModel",
                                "New post: $newPost"
                            )
                            _postsState.update {
                                it.copy(ourPost = newPost)
                            }
                            Log.d(
                                "ChallengeDetailScreen ChallengeDetailViewModel",
                                "final list : ${_postsState.value.posts}"
                            )
                        }

                        is ResultState.Error -> {
                            Log.e(
                                "ChallengeDetailScreen ChallengeDetailViewModel",
                                "Error fetching user: ${result.message}"
                            )
                        }

                        is ResultState.Loading -> {

                        }
                    }
                }
        }
    }
}