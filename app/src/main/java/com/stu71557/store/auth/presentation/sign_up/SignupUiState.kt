package com.stu71557.store.auth.presentation.sign_up

import com.stu71557.store.products.domain.model.ApiError

data class SignupUiState(
    val isLoading: Boolean = false,
    val signupIsSuccessful: Boolean = false,
    val error: ApiError? = null
)