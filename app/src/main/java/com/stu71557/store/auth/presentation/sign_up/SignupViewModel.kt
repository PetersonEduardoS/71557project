package com.stu71557.store.auth.presentation.sign_up

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.auth.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignupViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(SignupUiState())
    val uiState = _uiState.asStateFlow()

    fun signup(user: User) = viewModelScope.launch {
        val isLoading: (Boolean) -> Unit = { loading ->
            _uiState.update { it.copy(isLoading = loading) }
        }

        isLoading(true)

        authRepository.signup(user).onRight {
            isLoading(false)
            _uiState.update { it.copy(signupIsSuccessful = true) }
        }.onLeft {  networkError ->
            _uiState.update {
                it.copy(error = networkError.error)
            }
            isLoading(false)
        }

    }
}