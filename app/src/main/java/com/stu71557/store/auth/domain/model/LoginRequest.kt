package com.stu71557.store.auth.domain.model

data class LoginRequest(
    val username: String,
    val email: String = "none",
    val password: String
)
