package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.Product

interface ProductRepository {
    suspend fun getProductById(productId: String): Product
}