package com.epam.grocerystoreapp.domain.use_cases.scan

import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.domain.repository.ScanRepository
import javax.inject.Inject

class GetProductByBarcodeUseCase @Inject constructor(
    private val repository: ScanRepository,
) {

    suspend operator fun invoke(barcode: String): CatalogItem {
        return repository.getProductByBarcode(barcode)
    }
}
