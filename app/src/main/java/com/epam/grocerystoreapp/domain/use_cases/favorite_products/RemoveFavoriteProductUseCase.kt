package com.epam.grocerystoreapp.domain.use_cases.favorite_products

import com.epam.grocerystoreapp.data.repository.FavoriteProductsRepositoryImpl
import javax.inject.Inject

class RemoveFavoriteProductUseCase @Inject constructor(
    private val repository: FavoriteProductsRepositoryImpl,
) {

    suspend operator fun invoke(productId: String) {
        repository.removeFavoriteProduct(productId)
    }

}
