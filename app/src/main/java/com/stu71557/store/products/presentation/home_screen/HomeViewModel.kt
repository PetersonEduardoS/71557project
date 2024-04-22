package com.stu71557.store.products.presentation.home_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71557.store.products.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productsRepository: ProductsRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeUiState())
    val state = _state.asStateFlow()

    init {
        getProducts()
        getProductCategories()
    }

    fun getProducts(category: String = "All") {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            productsRepository.getProducts().onRight { products ->
                val filteredProducts = when (category) {
                    "All" -> {
                        products
                    }

                    else -> {
                        products.filter { product ->
                            product.category == category
                        }
                    }
                }.sortedBy { product -> product.title }
                _state.update {
                    it.copy(products = filteredProducts, categorySelected = category)
                }

            }
            _state.update { it.copy(isLoading = false) }
        }
    }

    private fun getProductCategories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }

            _state.update {
                it.copy(productCategories = productsRepository.getProductCategories())
            }
            _state.update { it.copy(isLoading = false) }

        }
    }

    private val _searchedProducts = MutableStateFlow(_state.value.products)
    val searchedProducts = _searchedProducts.asStateFlow()

    fun onSearchTextChange(text: String){
        _searchedProducts.update {
            when {
                text.isNotEmpty() -> _state.value.products.filter { product ->
                    product.title.contains(text.trim(), ignoreCase = true)
                }.sortedBy { product -> product.title }

                else -> emptyList()
            }
        }
    }
}