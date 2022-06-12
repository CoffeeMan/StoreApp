package com.epam.grocerystoreapp.domain.use_cases.cart

import com.epam.grocerystoreapp.domain.repository.CartRepository
import javax.inject.Inject

class GetProductQuantityInCartUseCase @Inject constructor(
    private val cartRepository: CartRepository,
){

    suspend operator fun invoke(productId: String): Double? {
        return cartRepository.getProductQuantityInCart(productId)
    }

}
