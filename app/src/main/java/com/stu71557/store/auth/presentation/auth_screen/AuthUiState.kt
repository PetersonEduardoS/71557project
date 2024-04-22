package com.stu71557.store.auth.presentation.auth_screen

import com.stu71557.store.products.domain.model.ApiError

data class AuthUiState(
    val error: ApiError? = null,
    val loginIsSuccessful: Boolean = false,
    val isLoading: Boolean = false,
)