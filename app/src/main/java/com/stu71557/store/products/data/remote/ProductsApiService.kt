package com.stu71557.store.products.data.remote

import com.stu71557.store.products.domain.model.Product
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductsApiService {

    @GET("products")
    suspend fun getProducts(): List<Product>

    @GET("products/categories")
    suspend fun getProductCategories(): List<String>

    @GET("products/{id}")
    suspend fun getProduct(@Path("id") id: Int): Product
}