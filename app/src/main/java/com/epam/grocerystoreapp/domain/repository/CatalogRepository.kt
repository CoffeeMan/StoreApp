package com.epam.grocerystoreapp.domain.repository

import androidx.paging.PagingSource
import com.epam.grocerystoreapp.data.model.ProductGeneral
import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.domain.model.ProductCategory
import com.epam.grocerystoreapp.domain.model.catalog.FilterBy
import com.epam.grocerystoreapp.domain.model.catalog.SortBy
import kotlinx.coroutines.flow.StateFlow

interface CatalogRepository {
    suspend fun getAllProducts(): List<CatalogItem>
    suspend fun getProductsByName(searchQuery: String): List<CatalogItem>
    fun getProductsByCategory(filterBy: FilterBy, category: ProductCategory?): PagingSource<Int, ProductGeneral>
    suspend fun reverseFavoriteMark(productId: String)
    suspend fun loadProducts()
    fun getAllCategories(): List<String>
    suspend fun getFavoriteProducts(): List<CatalogItem>
    suspend fun getProductByCode(code: String): CatalogItem
    suspend fun getProductsPage(): StateFlow<ProductGeneral>
}