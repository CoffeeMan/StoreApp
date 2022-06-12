package com.epam.grocerystoreapp.domain.use_cases.cart

import com.epam.grocerystoreapp.domain.model.CartItem
import com.epam.grocerystoreapp.domain.repository.CartRepository
import javax.inject.Inject

class GetAllCartItemsUseCase @Inject constructor(
    private val repository: CartRepository,
) {

    suspend operator fun invoke(): List<CartItem> {
        return repository.getAllProductsInCart()
    }

}
