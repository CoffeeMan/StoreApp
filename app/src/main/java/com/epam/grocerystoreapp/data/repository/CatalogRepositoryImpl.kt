package com.epam.grocerystoreapp.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.epam.grocerystoreapp.data.api.ApiProductService
import com.epam.grocerystoreapp.data.database.catalog.CatalogDao
import com.epam.grocerystoreapp.data.model.FavoriteProduct
import com.epam.grocerystoreapp.data.model.ProductGeneral
import com.epam.grocerystoreapp.data.network.ProductsPageSource
import com.epam.grocerystoreapp.data.utils.toCatalogEntities
import com.epam.grocerystoreapp.data.utils.toCatalogItems
import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.domain.model.ProductCategory
import com.epam.grocerystoreapp.domain.model.catalog.FilterBy
import com.epam.grocerystoreapp.domain.model.catalog.SortBy
import com.epam.grocerystoreapp.domain.repository.CatalogRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CatalogRepositoryImpl @Inject constructor(
    private val catalogDao: CatalogDao,
    private val apiProductService: ApiProductService,
) : CatalogRepository {

    override suspend fun getAllProducts(): List<CatalogItem> {
        return catalogDao.getAllCatalogEntities().toCatalogItems()
    }

    override suspend fun getProductsByName(searchQuery: String): List<CatalogItem> {
        return catalogDao.getCatalogEntitiesByName(searchQuery = searchQuery).toCatalogItems()
    }

    override fun getProductsByCategory(
        filterBy: FilterBy,
        category: ProductCategory?
    ): PagingSource<Int, ProductGeneral> {
        return ProductsPageSource(
            productService = apiProductService,
            filterBy = filterBy,
            category = category
        )
    }

    override suspend fun reverseFavoriteMark(productId: String) {
        val oldEntity = catalogDao.getCatalogEntityById(productId)
        val newEntity = oldEntity.copy(isFavorite = !oldEntity.isFavorite)
        catalogDao.insertCatalogEntity(newEntity)

        saveFavoriteProductToServer(productId)
    }

    override suspend fun loadProducts() {
        withContext(Dispatchers.IO) {
            val productsGeneral = async { getProductsGeneralFromServer() }
            val favoriteProducts = async { getFavoriteProductsFromServer() }
            insertProductsToDb(
                productsGeneral = productsGeneral.await(),
                favoriteProducts = favoriteProducts.await()
            )
        }
    }

    override suspend fun getFavoriteProducts(): List<CatalogItem> {
        return catalogDao.getFavoriteCatalogEntities().toCatalogItems()
    }

    override fun getAllCategories(): List<String> {
        return ProductCategory.values().map { productCategory -> productCategory.name }
    }

    private suspend fun insertProductsToDb(
        productsGeneral: List<ProductGeneral>,
        favoriteProducts: List<FavoriteProduct>,
    ) {
        val catalogEntities = productsGeneral.toCatalogEntities(favoriteProducts = favoriteProducts)
        catalogDao.insertCatalogEntities(catalogEntities)
    }

    private suspend fun getFavoriteProductsFromServer(): List<FavoriteProduct> {
        TODO()
    }

    private suspend fun saveFavoriteProductToServer(productId: String) {
        TODO()
    }

    private suspend fun getProductsGeneralFromServer(): List<ProductGeneral> {
        val result = apiProductService.getProductsList()
        Log.e("ProductsList ", "products: " + result.products)
        return result.products
    }

    override suspend fun getProductByCode(code: String): CatalogItem {
        TODO()
    }

    override suspend fun getProductsPage(): StateFlow<ProductGeneral> {
        TODO("Not yet implemented")
    }

}