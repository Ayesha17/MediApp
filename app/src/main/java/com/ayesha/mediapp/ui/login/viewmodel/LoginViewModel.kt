package com.ayesha.mediapp.ui.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ayesha.core.ViewState
import com.ayesha.core.di.CoreModule
import com.ayesha.mediapp.R
import com.ayesha.mediapp.data.database.entities.User
import com.ayesha.mediapp.domain.UserAuthUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userAuthUseCase: UserAuthUseCase,
    @CoreModule.IoDispatcher val ioDispatcher: CoroutineDispatcher
) : ViewModel() {
    private val _loginUiState = MutableStateFlow(LoginUiState())
    val loginUiState: StateFlow<LoginUiState> = _loginUiState

    fun onUserNameChange(userName: String) {
        _loginUiState.update {
            it.copy(
                userName = userName,
            )
        }
    }

    fun onEmailChange(email: String) {
        _loginUiState.update {
            it.copy(
                email = email,
            )
        }
    }
     fun clearUiState() {
            _loginUiState.update {
                it.copy(
                   viewState = ViewState.EMPTY
                )
            }
        }

    fun onLoginButtonClick() {
        if(loginUiState.value.userName.isEmpty() || loginUiState.value.email.isEmpty()){
            _loginUiState.update {
                it.copy(
                    viewState = ViewState.ERROR,
                    errorMessage =  R.string.please_fill_the_required_fields
                )
            }
        }else {
            _loginUiState.update {
                it.copy(
                    viewState = ViewState.LOADING,
                )
            }
            viewModelScope.launch(ioDispatcher) {
                userAuthUseCase(
                    User(
                        userName = loginUiState.value.userName,
                        email = loginUiState.value.email
                    )
                ) { authResult ->
                    authResult.result(onSuccess = {
                        _loginUiState.update {
                            it.copy(
                                viewState = ViewState.SUCCESS,
                            )
                        }

                    }, onError = {
                        _loginUiState.update {
                            it.copy(
                                viewState = ViewState.ERROR,
                            )
                        }
                    })

                }
            }
        }
    }

}

data class LoginUiState(
    val viewState: ViewState = ViewState.EMPTY,
    val errorMessage: Int = R.string.empty,
    val userName: String = "",
    val email: String = "",
)