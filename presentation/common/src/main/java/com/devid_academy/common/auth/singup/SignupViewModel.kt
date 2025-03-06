package com.devid_academy.common.auth.singup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.ApiResult
import com.devid_academy.auth.SignupDto
import com.devid_academy.auth.UserRepository
import com.devid_academy.common.R
import com.devid_academy.ui.GlobalMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userRepository: UserRepository,
    private val globalMessageRepository: GlobalMessageRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<SignupUiState>(SignupUiState.Initial)
    val uiState = _uiState.asStateFlow()

    enum class LoginError {
        EMAIL_EXISTS,
        USERNAME_EXISTS
    }

    fun signup(signupDto: SignupDto) {
        viewModelScope.launch {
            when (val response = userRepository.signup(signupDto)) {
                is ApiResult.Success -> {
                    globalMessageRepository.userMessageStringRes.tryEmit(
                        R.string.user_message_user_signed_in
                    )
                    _uiState.value = SignupUiState.Success
                }
                is ApiResult.Error -> {
                    if (response.httpCode == 400) {
                        when(response.errorCode) {
                            LoginError.EMAIL_EXISTS.name -> {
                                globalMessageRepository.userMessageStringRes.tryEmit(
                                    R.string.user_message_email_already_used
                                )
                            }
                            LoginError.USERNAME_EXISTS.name -> {
                                globalMessageRepository.userMessageStringRes.tryEmit(
                                    R.string.user_message_username_already_used
                                )
                            }
                            else -> {
                                globalMessageRepository.userMessageString.tryEmit(
                                    "${response.httpCode} : ${response.exception}"
                                )
                            }
                        }
                        _uiState.value = SignupUiState.Error(response.exception.message ?: "Unknown error")
                    } else {
                        globalMessageRepository.userMessageString.tryEmit(
                            "${response.httpCode} : ${response.exception}"
                        )
                        _uiState.value = SignupUiState.Error(response.exception.message ?: "Unknown error")
                    }
                }
            }
        }
    }
}

