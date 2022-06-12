package com.epam.grocerystoreapp.domain.repository

interface FavoriteProductsRepository {
    suspend fun getAllFavoriteProducts(): List<String>
    suspend fun removeFavoriteProduct(productId: String)
    suspend fun addFavoriteProduct(productId: String)
    suspend fun isProductFavorite(productId: String): Boolean
}