package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.CatalogItem

interface ScanRepository {
    suspend fun getProductByBarcode(barcode: String): CatalogItem
}
