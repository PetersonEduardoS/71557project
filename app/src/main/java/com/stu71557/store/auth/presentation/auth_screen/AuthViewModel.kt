package com.stu71557.store.auth.presentation.auth_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71557.store.auth.domain.model.LoginRequest
import com.stu71557.store.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AuthUiState())
    val state = _state.asStateFlow()


    fun login(loginRequest: LoginRequest){
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            authRepository.login(loginRequest)
                .onRight { _ ->
                    _state.update {
                        it.copy(error = null, loginIsSuccessful = true, )
                    }
                }
                .onLeft { networkError ->
                _state.update {
                    it.copy(error = networkError.error, loginIsSuccessful = false, isLoading = false)
                }
            }
        }
    }
}