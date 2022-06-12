package com.epam.grocerystoreapp.presentation.model

import com.epam.grocerystoreapp.domain.model.ProductUnit

data class CartItemUi(
    val id: String,
    val name: String,
    val photoUrl: String,
    val price: Double,
    val priceWithDiscount: Double,
    val priceWithDiscountVisibility: Int,
    val quantity: Double,
    val variableQuantity: Boolean,
    val unit: ProductUnit,
)
