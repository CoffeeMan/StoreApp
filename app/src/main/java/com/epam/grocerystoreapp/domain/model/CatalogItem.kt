package com.epam.grocerystoreapp.domain.model

data class CatalogItem(
    val id: String,
    val name: String,
    val category: ProductCategory?,
    val price: Double,
    val description: String,
    val photoUrl: String,
    val priceWithDiscount: Double,
    val isFavorite: Boolean,
    val unit: ProductUnit,
)
