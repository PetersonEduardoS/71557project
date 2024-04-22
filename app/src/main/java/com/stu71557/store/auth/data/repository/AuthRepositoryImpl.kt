package com.stu71557.store.auth.data.repository

import arrow.core.Either
import com.stu71557.store.auth.data.local.UserDao
import com.stu71557.store.auth.domain.model.LoginRequest
import com.stu71557.store.auth.domain.model.LoginResponse
import com.stu71557.store.auth.domain.model.User
import com.stu71557.store.auth.domain.repository.AuthRepository
import com.stu71557.store.products.data.mapper.toNetworkError
import com.stu71557.store.products.domain.model.NetworkError
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao
): AuthRepository {
    override suspend fun login(loginRequest: LoginRequest): Either<NetworkError, LoginResponse> {
        return Either.catch {
            saveUser(loginRequest)
            LoginResponse("none")
        }.mapLeft {
            it.toNetworkError()
        }
    }

    override suspend fun signup(user: User): Either<NetworkError, User> {
        return Either.catch {
            userDao.upsert(user)
            user
        }.mapLeft {
            it.toNetworkError()
        }
    }

    override suspend fun getUser(id: Int): Either<NetworkError, User> {
        return Either.catch {
            userDao.getUser(id)!!
        }.mapLeft {
            it.toNetworkError()
        }
    }

   private suspend fun saveUser(loginRequest: LoginRequest){
        val user = userDao.getUser() ?: User(6, "none", "none", "none", false)
        signup(user.copy(name = loginRequest.username, email = loginRequest.email, password = loginRequest.password))
    }
}