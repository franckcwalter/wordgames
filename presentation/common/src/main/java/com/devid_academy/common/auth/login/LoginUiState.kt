package com.devid_academy.common.auth.login

sealed class LoginUiState {
    data object Initial : LoginUiState()
    data object Success : LoginUiState()
    data class Error(val message: String) : LoginUiState()
}