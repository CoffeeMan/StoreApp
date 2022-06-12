package com.epam.grocerystoreapp.domain.use_cases.cart

import com.epam.grocerystoreapp.domain.repository.CartRepository
import javax.inject.Inject

class AddCartItemUseCase @Inject constructor(
    private val repository: CartRepository,
) {

    suspend operator fun invoke(
        id: String,
        quantity: Double,
        variableQuantity: Boolean,
    ) {
        repository.addProductToCart(
            id = id,
            quantity = quantity,
            variableQuantity = variableQuantity
        )
    }

}
