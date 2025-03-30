package com.test.features.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.test.domain.utils.onError
import com.test.domain.utils.onLoading
import com.test.domain.utils.onSuccess
import com.test.domain.usecase.GetEventsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeChallengeViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _viewState = MutableStateFlow(HomeChallengeState())
    val viewState: StateFlow<HomeChallengeState>
        get() = _viewState.map { challengeViewState ->
            challengeViewState.copy(events = challengeViewState.events.filter { challenge ->
                challenge.title.uppercase()
                    .contains(challengeViewState.searchText.trim().uppercase())
            })
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _viewState.value
        )

    init {
        fetchChallenges()
    }

    private fun fetchChallenges() {
        viewModelScope.launch {
            getEventsUseCase.execute().collect { result ->
                result.onLoading {
                    _viewState.update { it.copy(isLoading = true) }
                }.onError {
                    _viewState.update { it.copy(isLoading = false, error = it.error) }
                }.onSuccess { challenges ->
                    _viewState.update { it.copy(isLoading = false, events = challenges) }
                }
            }
        }
    }

    fun onSearchTextChange(text: String) {
        _viewState.update {
            it.copy(searchText = text)
        }
    }
}