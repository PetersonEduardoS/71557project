package com.stu71557.store.cart.domain.converters

import androidx.room.TypeConverter
import com.stu71557.store.cart.domain.model.ProductInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class ProductInfoConverter {

    @TypeConverter
    fun fromProducts(products: List<ProductInfo>): String {
        return Json.encodeToString(products)
    }

    @TypeConverter
    fun toProducts(products: String): List<ProductInfo> {
        return Json.decodeFromString(products)
    }
}