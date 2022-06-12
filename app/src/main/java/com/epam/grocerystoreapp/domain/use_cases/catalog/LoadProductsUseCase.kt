package com.epam.grocerystoreapp.domain.use_cases.catalog

import com.epam.grocerystoreapp.domain.repository.CatalogRepository
import javax.inject.Inject

class LoadProductsUseCase @Inject constructor(
    private val repository: CatalogRepository,
) {

    suspend operator fun invoke() {
        repository.loadProducts()
    }

}
