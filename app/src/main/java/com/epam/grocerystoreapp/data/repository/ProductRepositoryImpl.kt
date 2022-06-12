package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.data.api.ApiProductService
import com.epam.grocerystoreapp.data.utils.toProduct
import com.epam.grocerystoreapp.domain.model.Product
import com.epam.grocerystoreapp.domain.repository.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val apiProductService: ApiProductService,
) : ProductRepository {

    override suspend fun getProductById(productId: String): Product {
        return apiProductService.getProductByCode(productId).products.first().toProduct()
    }

}
