package com.stu71557.store.products.presentation.product_details

import com.stu71557.store.products.domain.model.Product

data class ProductDetailsUiState(
    val product: Product = Product(),
    val isLoading: Boolean = true
)
