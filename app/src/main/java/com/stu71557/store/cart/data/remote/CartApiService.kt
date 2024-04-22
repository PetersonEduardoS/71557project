package com.stu71557.store.cart.data.remote

import com.stu71557.store.cart.domain.model.Cart
import retrofit2.http.GET
import retrofit2.http.Path

interface CartApiService {

    @GET("carts/user/{id}")
    suspend fun getCart(@Path("id") userId: Int): List<Cart>
}