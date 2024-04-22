package com.stu71557.store.profile.presentation

import com.stu71557.store.auth.domain.model.User

data class ProfileUiState(
    val user: User = User(0, "", "", "", false),
    val isLoading: Boolean = true
)