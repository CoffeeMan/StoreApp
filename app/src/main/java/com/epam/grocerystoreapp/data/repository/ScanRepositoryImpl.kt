package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.data.api.ApiProductService
import com.epam.grocerystoreapp.data.utils.toCatalogItem
import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.domain.repository.ScanRepository
import javax.inject.Inject

class ScanRepositoryImpl @Inject constructor(
    private val apiProductService: ApiProductService
): ScanRepository {

    override suspend fun getProductByBarcode(barcode: String): CatalogItem {
        return apiProductService.getProductByCode(barcode).products.first().toCatalogItem()
    }
}
