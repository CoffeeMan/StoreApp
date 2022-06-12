package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.data.api.ApiProductService
import com.epam.grocerystoreapp.data.database.cart.CartDao
import com.epam.grocerystoreapp.data.database.cart.CartEntity
import com.epam.grocerystoreapp.domain.model.CartItem
import com.epam.grocerystoreapp.domain.model.ProductUnit
import com.epam.grocerystoreapp.domain.repository.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val cartDao: CartDao,
    private val apiProductService: ApiProductService,
) : CartRepository {

    override suspend fun getAllProductsInCart(): List<CartItem> {
        return cartDao.getAllCartEntities().map { cartEntity ->
            val productGeneral = apiProductService.getProductByCode(cartEntity.id).products.first()
            CartItem(
                id = cartEntity.id,
                name = productGeneral.name,
                price = productGeneral.price,
                priceWithDiscount = productGeneral.priceWithDiscount,
                photoUrl = productGeneral.photoUrl,
                quantity = cartEntity.quantity,
                variableQuantity = cartEntity.variableQuantity,
                unit = ProductUnit.valueOf(productGeneral.unit.uppercase())
            )
        }
    }

    override suspend fun getProductQuantityInCart(productId: String): Double? {
        return cartDao.getCartEntityById(productId)?.quantity
    }

    override suspend fun addProductToCart(
        id: String,
        quantity: Double,
        variableQuantity: Boolean,
    ) {
        cartDao.insertCartEntity(
            CartEntity(
                id = id,
                quantity = quantity,
                variableQuantity = variableQuantity
            )
        )
    }

    override suspend fun deleteProductFromCart(productId: String) {
        cartDao.deleteCartEntity(id = productId)
    }

    override suspend fun increaseProductQuantity(productId: String, amount: Double) {
        cartDao.increaseQuantity(id = productId, amount = amount)
    }

    override suspend fun decreaseProductQuantity(productId: String, amount: Double) {
        val cartEntity = cartDao.getCartEntityById(productId)
        val productGeneral = apiProductService.getProductByCode(productId).products.first()
        val unit = ProductUnit.valueOf(productGeneral.unit.uppercase())

        if (cartEntity != null && cartEntity.quantity > unit.discreteValue) {
            cartDao.decreaseQuantity(id = productId, amount = amount)
        } else {
            cartDao.deleteCartEntity(id = productId)
        }
    }

    override suspend fun clearCart() {
        cartDao.clearCart()
    }

}
