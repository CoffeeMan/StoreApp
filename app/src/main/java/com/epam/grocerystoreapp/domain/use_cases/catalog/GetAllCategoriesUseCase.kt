package com.epam.grocerystoreapp.domain.use_cases.catalog

import com.epam.grocerystoreapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetAllCategoriesUseCase @Inject constructor(
    private val repository: CatalogRepository
) {
    operator fun invoke(): List<String> {
        return repository.getAllCategories()
    }
}