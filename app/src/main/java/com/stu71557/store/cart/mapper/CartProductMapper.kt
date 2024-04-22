package com.stu71557.store.cart.mapper

import com.stu71557.store.cart.domain.model.ProductSimple
import com.stu71557.store.products.domain.model.Product

fun Product.toProductSimple(quantity: Int): ProductSimple {
    return  ProductSimple(
        id = id,
        title = title,
        image = image,
        price =  price,
        quantity = quantity
    )
}