package com.stu71557.store.auth.domain.repository

import arrow.core.Either
import com.stu71557.store.auth.domain.model.LoginRequest
import com.stu71557.store.auth.domain.model.LoginResponse
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.products.domain.model.NetworkError

interface AuthRepository {
    suspend fun login(loginRequest: LoginRequest): Either<NetworkError, LoginResponse>

    suspend fun signup(user: User): Either<NetworkError, User>

    suspend fun getUser(id: Int = 6): Either<NetworkError, User>
}