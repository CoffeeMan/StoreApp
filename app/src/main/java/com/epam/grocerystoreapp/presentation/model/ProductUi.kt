package com.epam.grocerystoreapp.presentation.model

import com.epam.grocerystoreapp.domain.model.ProductUnit

data class ProductUi(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val priceWithoutDiscount: String,
    val priceWithDiscount: String,
    val priceWithoutDiscountVisibility: Int,
    val isFavorite: Boolean,
    val unit: ProductUnit,
)
