package com.devid_academy.common.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.LoginDto
import com.devid_academy.auth.UserRepository
import com.devid_academy.common.R
import com.devid_academy.auth.ApiResult
import com.devid_academy.ui.GlobalMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val userRepository: UserRepository,
    private val globalMessageRepository : GlobalMessageRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<LoginUiState>(LoginUiState.Initial)
    val uiState = _uiState.asStateFlow()

    fun login(loginDto: LoginDto) {
        viewModelScope.launch {
            when(val response = userRepository.login(loginDto)) {
                is ApiResult.Success -> {
                    globalMessageRepository.userMessageStringRes.tryEmit(
                        R.string.user_message_user_logged_in
                    )
                    _uiState.value = LoginUiState.Success
                }
                is ApiResult.Error -> {
                    if (response.httpCode == 401) {
                        globalMessageRepository.userMessageStringRes.tryEmit(
                            R.string.user_message_wrong_email_or_password
                        )
                    } else {
                        globalMessageRepository.userMessageString.tryEmit(
                            "${response.httpCode} : ${response.exception}"
                        )
                        Log.e("login", response.toString())
                    }
                    _uiState.value = LoginUiState.Error(response.exception.message ?: "Unknown error")
                }
            }
        }
    }
}
