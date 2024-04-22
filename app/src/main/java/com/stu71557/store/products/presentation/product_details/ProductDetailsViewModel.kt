package com.stu71557.store.products.presentation.product_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71557.store.cart.domain.model.ProductInfo
import com.stu71557.store.cart.domain.repository.CartRepository
import com.stu71557.store.products.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val productsRepository: ProductsRepository,
    private val cartRepository: CartRepository
) : ViewModel() {
    private val productId = savedStateHandle.get<Int>("productId")!!
    private val _state = MutableStateFlow(ProductDetailsUiState())
    val state = _state.asStateFlow()

    init {
        getProduct()
    }

    private fun getProduct() {
        viewModelScope.launch {
            _state.update {
                it.copy(isLoading = true)
            }
            _state.update {
                it.copy(product = productsRepository.getProductById(productId))
            }
            _state.update {
                it.copy(isLoading = false)
            }
        }
    }

    fun addProductToCart(quantity: Int) {
        viewModelScope.launch {
            cartRepository.getCart().onRight { carts ->

                val wantedCart = carts.find { it.id == 3 }

                wantedCart?.let { cart ->
                    val newProductInfo = ProductInfo(
                        _state.value.product.id, quantity
                    )
                    val products = listOf(newProductInfo) + cart.products
                    cartRepository.upsert(
                        cart.copy(
                            products = products
                        )
                    )
                }
            }
        }
    }
}