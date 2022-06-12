package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.CartItem
import com.epam.grocerystoreapp.domain.model.CatalogItem

interface CartRepository {

    suspend fun getAllProductsInCart(): List<CartItem>
    suspend fun getProductQuantityInCart(productId: String): Double?
    suspend fun addProductToCart(
        id: String,
        quantity : Double,
        variableQuantity: Boolean,
    )
    suspend fun deleteProductFromCart(productId: String)
    suspend fun increaseProductQuantity(productId: String, amount: Double)
    suspend fun decreaseProductQuantity(productId: String, amount: Double)
    suspend fun clearCart()

}
