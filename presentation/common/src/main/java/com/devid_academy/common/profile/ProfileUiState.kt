package com.devid_academy.common.profile

sealed class ProfileUiState {
    data class Success(
        val username: String = "",
        val email: String = "",
        val totalPoints: Int = 0
    ) : ProfileUiState()
    data object Loading : ProfileUiState()
    data object LoggedOut : ProfileUiState()
    data class Error(val message: String) : ProfileUiState()
}