package com.epam.grocerystoreapp.presentation.model

import com.epam.grocerystoreapp.domain.model.ProductUnit

data class CatalogItemUi(
    val id: String,
    val name: String,
    val category: String,
    val price: String,
    val description: String,
    val photoUrl: String,
    val discount: String,
    val isFavorite: Boolean,
    val unit: ProductUnit,
)
