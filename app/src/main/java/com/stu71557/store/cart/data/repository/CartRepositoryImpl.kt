package com.stu71557.store.cart.data.repository

import arrow.core.Either
import com.stu71557.store.cart.data.local.datasource.CartDao
import com.stu71557.store.cart.data.remote.CartApiService
import com.stu71557.store.cart.domain.model.Cart
import com.stu71557.store.cart.domain.repository.CartRepository
import com.stu71557.store.products.data.mapper.toNetworkError
import com.stu71557.store.products.domain.model.NetworkError
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartApiService: CartApiService, private val cartDao: CartDao
) : CartRepository {

    override suspend fun getCart(userId: Int): Either<NetworkError, List<Cart>> {
        return Either.catch {
            val cartFromDao = cartDao.getCartById()
            val cartFromApi = cartApiService.getCart(userId)

            val combinedList = mutableListOf<Cart>().apply {
                if (cartFromDao != null) {
                    add(cartFromDao)
                }
                addAll(cartFromApi)
            }

            combinedList
        }.mapLeft {
            it.toNetworkError()
        }
    }

    override suspend fun delete(cart: Cart) {
        cartDao.delete(cart)
    }

    override suspend fun upsert(cart: Cart) {
        cartDao.upsert(cart)
    }
}