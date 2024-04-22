package com.stu71557.store.cart.domain.model

data class ProductSimple(
    val id: Int = 0,
    val title: String = "",
    val price: Double = 0.0,
    val image: String = "",
    val quantity: Int = 0
)
