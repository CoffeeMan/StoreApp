package com.epam.grocerystoreapp.domain.use_cases.catalog

import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetProductByCodeUseCase @Inject constructor(
    private val repository: CatalogRepository,
) {
    suspend operator fun invoke(code: String): CatalogItem {
        return repository.getProductByCode(code)
    }
}