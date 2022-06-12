package com.epam.grocerystoreapp.presentation.model

import com.epam.grocerystoreapp.domain.model.CatalogItem

data class CatalogProductData(
    val catalogProduct: CatalogItem,
    val hideDiscount: Boolean = true
)
