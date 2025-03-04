package com.devid_academy.common.auth.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.LoginDto
import com.devid_academy.auth.UserRepository
import kotlinx.coroutines.launch


class LoginViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    //private val uiState = MutableStateFlow(LoginUiState())
    // fun observeUiState(): StateFlow<LoginUiState> = uiState.asStateFlow()

    fun login(loginDto: LoginDto) {

        viewModelScope.launch {
            val response = userRepository.login(loginDto)

            Log.e("login", response.toString())
        }
    }



}
