package com.stu71557.store.profile.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71557.store.auth.domain.model.LoginRequest
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.auth.domain.repository.AuthRepository
import com.stu71557.store.auth.presentation.auth_screen.AuthUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
): ViewModel() {



    private val _state = MutableStateFlow(ProfileUiState())
    val state = _state.asStateFlow()

    init {
        getUser()
    }

    private fun getUser() = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        authRepository.getUser().onRight {  user ->
            _state.update {
                it.copy(user = user, isLoading = false)
            }
        }

    }

    fun saveUser(user: User) = viewModelScope.launch {
        _state.update { it.copy(isLoading = true) }
        authRepository.login(LoginRequest(user.name, user.email, user.password)).onRight {
            getUser()
        }
    }
}