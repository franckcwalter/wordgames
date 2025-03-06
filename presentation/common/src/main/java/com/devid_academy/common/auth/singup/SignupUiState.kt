package com.devid_academy.common.auth.singup

sealed class SignupUiState {
    data object Initial : SignupUiState()
    data object Success : SignupUiState()
    data class Error(val message: String) : SignupUiState()
}