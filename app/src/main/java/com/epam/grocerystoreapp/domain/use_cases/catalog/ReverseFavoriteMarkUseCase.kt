package com.epam.grocerystoreapp.domain.use_cases.catalog

import com.epam.grocerystoreapp.domain.repository.CatalogRepository
import javax.inject.Inject

class ReverseFavoriteMarkUseCase @Inject constructor(
    private val repository: CatalogRepository,
) {

    suspend operator fun invoke(productId: String) {
        repository.reverseFavoriteMark(productId)
    }

}
