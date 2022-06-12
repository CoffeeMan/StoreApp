package com.epam.grocerystoreapp.presentation.model

import com.epam.grocerystoreapp.presentation.utils.ProductItemAction

data class ProductListItemAction(val productId: String, val actionType: ProductItemAction)
