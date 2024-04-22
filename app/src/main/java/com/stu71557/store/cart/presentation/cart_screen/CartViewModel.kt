package com.stu71557.store.cart.presentation.cart_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stu71557.store.cart.domain.repository.CartRepository
import com.stu71557.store.cart.mapper.toProductSimple
import com.stu71557.store.products.domain.repository.ProductsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository, private val productsRepository: ProductsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CartUiState())
    val state = _state.asStateFlow()

    init {
        getAllProducts()
    }

    private fun getAllProducts() {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }
            delay(500)
            cartRepository.getCart(2).onRight { carts ->

                val products = carts.flatMap { cart ->
                    cart.products.map { productInfo ->
                        productsRepository.getProductById(productInfo.productId)
                            .toProductSimple(productInfo.quantity)
                    }
                }

                _state.update {
                    it.copy(
                        products = products
                    )
                }

                calculateTotal()
                _state.update {
                    it.copy(
                        isLoading = false
                    )
                }
            }.onLeft {
                Log.i("Cart", it.error.message)
            }
        }
    }

    fun removeProduct(id: Int) = viewModelScope.launch {
        cartRepository.getCart().onRight { carts ->
            val wantedCart = carts.find { it.id == 3 }
            wantedCart?.let { cart ->
                val products = cart.products.toMutableList()
                val updatedCart = products.find { it.productId == id }
                products.remove(updatedCart)

                cartRepository.upsert(
                    cart.copy(
                        products = products
                    )
                )

                getAllProducts()
            }
        }
    }

    private fun calculateTotal() = viewModelScope.launch {
        var total = 0.0

        _state.value.products.forEach {
            total += it.price
        }
        val formattedTotal = String.format("%.2f", total).replace(",", ".")

        _state.update { it.copy(subtotal = formattedTotal.toDouble()) }
    }

}
