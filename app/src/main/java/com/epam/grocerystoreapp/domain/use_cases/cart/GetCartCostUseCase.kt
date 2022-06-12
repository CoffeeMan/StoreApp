package com.epam.grocerystoreapp.domain.use_cases.cart

import com.epam.grocerystoreapp.domain.model.ProductUnit
import com.epam.grocerystoreapp.domain.repository.CartRepository
import javax.inject.Inject

class GetCartCostUseCase @Inject constructor(
    private val repository: CartRepository,
) {

    suspend operator fun invoke(): Double {
        val cartEntities = repository.getAllProductsInCart()
        var cartCost = 0.0
        cartEntities.forEach { cartEntity ->
            val quantity = when (cartEntity.unit) {
                ProductUnit.PIECES -> cartEntity.quantity
                ProductUnit.KG -> cartEntity.quantity
                ProductUnit.GRAM_100 -> cartEntity.quantity / 100
                ProductUnit.LITERS -> cartEntity.quantity
            }
            cartCost += cartEntity.priceWithDiscount * quantity
        }
        return cartCost
    }

}
