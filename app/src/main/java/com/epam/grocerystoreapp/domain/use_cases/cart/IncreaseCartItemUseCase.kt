package com.epam.grocerystoreapp.domain.use_cases.cart

import com.epam.grocerystoreapp.domain.model.ProductUnit
import com.epam.grocerystoreapp.domain.repository.CartRepository
import javax.inject.Inject

class IncreaseCartItemUseCase @Inject constructor(
    private val repository: CartRepository,
) {

    suspend operator fun invoke(productId: String, unit: ProductUnit) {
        return repository.increaseProductQuantity(
            productId = productId,
            amount = unit.discreteValue
        )
    }

}
