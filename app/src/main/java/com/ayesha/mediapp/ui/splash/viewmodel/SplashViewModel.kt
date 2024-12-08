package com.ayesha.mediapp.ui.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesha.core.ViewState
import com.ayesha.mediapp.domain.GetUserUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SplashViewModel @Inject constructor(private val getUserUseCase: GetUserUseCase) :
    ViewModel() {
    private val _splashUiState = MutableStateFlow(SplashUiState())
    val splashUiState: StateFlow<SplashUiState> = _splashUiState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() {
        viewModelScope.launch {
            getUserUseCase(Unit) { userResult ->
                userResult.result(onSuccess = { user ->
                    _splashUiState.update {
                        it.copy(viewState = ViewState.SUCCESS, userName = user.userName.orEmpty())
                    }
                }, onError = {})

            }
        }
    }
}

data class SplashUiState(
    val viewState: ViewState = ViewState.EMPTY,
    val userName: String = ""
)