package com.stu71557.store.cart.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import com.stu71557.store.cart.domain.converters.ProductInfoConverter

@Entity
data class Cart(
    @PrimaryKey
    val id: Int = 3,
    val userId: Int,
    val date: String,
    @TypeConverters(ProductInfoConverter::class)
    val products: List<ProductInfo>,
    @SerializedName("__v")
    val v: Int
)
