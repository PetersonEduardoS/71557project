package com.stu71557.store.cart.domain.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable


data class CartProduct(
    @SerializedName("id")
    val id: Int,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("date")
    val date: String,
    @SerializedName("products")
    val products: List<ProductInfo>,
)

@Serializable
data class ProductInfo(
    val productId: Int,
    val quantity: Int
)
