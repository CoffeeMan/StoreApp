package com.epam.grocerystoreapp.domain.model

data class CartItem(
    val id: String,
    val name: String,
    val photoUrl: String,
    val unit: ProductUnit,
    val price: Double,
    val priceWithDiscount: Double,
    val quantity: Double,
    val variableQuantity: Boolean,
)
