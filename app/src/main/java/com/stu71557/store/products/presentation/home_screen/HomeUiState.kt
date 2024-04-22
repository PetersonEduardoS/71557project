package com.stu71557.store.products.presentation.home_screen

import com.stu71557.store.products.domain.model.Product

data class HomeUiState(
    val products: List<Product> = emptyList(),
    val productCategories: List<String> = emptyList(),
    val isLoading: Boolean = true,
    val categorySelected: String = "All"
)
