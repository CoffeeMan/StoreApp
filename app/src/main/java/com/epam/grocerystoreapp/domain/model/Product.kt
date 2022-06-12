package com.epam.grocerystoreapp.domain.model

data class Product(
    val id: String,
    val name: String,
    val description: String,
    val photoUrl: String,
    val priceWithoutDiscount: Double,
    val priceWithDiscount: Double,
    val isFavorite: Boolean,
    val unit: ProductUnit,
)