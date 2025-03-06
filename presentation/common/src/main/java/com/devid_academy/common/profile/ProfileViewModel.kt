package com.devid_academy.common.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devid_academy.auth.UserRepository
import com.devid_academy.common.R
import com.devid_academy.ui.GlobalMessageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val globalMessageRepository: GlobalMessageRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfileUiState>(ProfileUiState.Loading)
    val uiState = _uiState.asStateFlow()

    fun getUserProfile() {
        viewModelScope.launch {
            _uiState.value = ProfileUiState.Success()
            // TODO: Implement profile fetching logic
            // _uiState.value = ProfileUiState.Success(...)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
            _uiState.value = ProfileUiState.LoggedOut
            globalMessageRepository.userMessageStringRes.tryEmit(
                R.string.user_message_user_logged_out
            )
        }
    }
}