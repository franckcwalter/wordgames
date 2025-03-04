package com.devid_academy.common.auth.singup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devid_academy.auth.SignupDto
import com.devid_academy.auth.UserRepository
import kotlinx.coroutines.launch

class SignupViewModel(
    private val userRepository: UserRepository
): ViewModel() {

    //private val uiState = MutableStateFlow(SignupUiState())
    //fun observeUiState(): StateFlow<SignupUiState> = uiState.asStateFlow()

    fun signup(signupDto: SignupDto) {

        viewModelScope.launch {
            val response = userRepository.signup(signupDto)

            Log.e("Singup", response.toString())
        }
    }


}

