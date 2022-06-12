package com.epam.grocerystoreapp.data.utils

import com.epam.grocerystoreapp.data.database.cart.CartDbItem
import com.epam.grocerystoreapp.data.database.catalog.CatalogEntity
import com.epam.grocerystoreapp.data.model.FavoriteProduct
import com.epam.grocerystoreapp.data.model.ProductGeneral
import com.epam.grocerystoreapp.domain.model.*

fun List<ProductGeneral>.toCatalogEntities(favoriteProducts: List<FavoriteProduct>): List<CatalogEntity> {
    return this.map { productGeneral ->
        CatalogEntity(
            id = productGeneral.id,
            name = productGeneral.name,
            category = ProductCategory.valueOf(productGeneral.category.uppercase()),
            price = productGeneral.price,
            photoUrl = productGeneral.photoUrl,
            priceWithDiscount = productGeneral.priceWithDiscount,
            description = productGeneral.description,
            isFavorite = favoriteProducts.contains(FavoriteProduct(productGeneral.id)),
            unit = ProductUnit.valueOf(productGeneral.unit.uppercase())
        )
    }
}

fun List<CatalogEntity>.toCatalogItems(): List<CatalogItem> {
    return this.map { productInCatalogEntity ->
        CatalogItem(
            id = productInCatalogEntity.id,
            name = productInCatalogEntity.name,
            category = productInCatalogEntity.category,
            price = productInCatalogEntity.price,
            photoUrl = productInCatalogEntity.photoUrl,
            priceWithDiscount = productInCatalogEntity.priceWithDiscount,
            description = productInCatalogEntity.description,
            isFavorite = productInCatalogEntity.isFavorite,
            unit = productInCatalogEntity.unit
        )
    }
}

fun ProductGeneral.toProduct(): Product {
    return Product(
        id = this.id,
        name = this.name,
        description = this.description,
        photoUrl = this.photoUrl,
        priceWithDiscount = this.priceWithDiscount,
        priceWithoutDiscount = this.price,
        unit = ProductUnit.valueOf(this.unit),
        isFavorite = false,
    )
}

fun CatalogItem.toCartEntity(quantity: Double, variableQuantity: Boolean): CartDbItem {
    return CartDbItem(
        id = this.id,
        name = this.name,
        price = this.price,
        photoUrl = this.photoUrl,
        discount = this.priceWithDiscount,
        quantity = quantity,
        variableQuantity = variableQuantity,
        unit = this.unit
    )
}

fun ProductGeneral.toCatalogItem(isFavorite: Boolean): CatalogItem {
    return CatalogItem(
        id = this.id,
        name = this.name,
        price = this.price,
        category = ProductCategory.fromString(this.category),
        photoUrl = this.photoUrl,
        priceWithDiscount = this.priceWithDiscount,
        description = this.description,
        unit = ProductUnit.valueOf(this.unit),
        isFavorite = isFavorite
    )
}

fun ProductGeneral.toCatalogItem(): CatalogItem {
    return CatalogItem(
        id = this.id,
        name = this.name,
        price = this.price,
        category = ProductCategory.fromString(this.category),
        photoUrl = this.photoUrl,
        priceWithDiscount = this.priceWithDiscount,
        description = this.description,
        unit = ProductUnit.KG,
        isFavorite = false
    )
}

fun List<CartDbItem>.toCartItems(): List<CartItem> {
    return this.map { cartEntity ->
        val priceWithDiscount = cartEntity.price * (1 - cartEntity.discount / 100)

        CartItem(
            id = cartEntity.id,
            name = cartEntity.name,
            price = cartEntity.price,
            photoUrl = cartEntity.photoUrl,
            priceWithDiscount = priceWithDiscount,
            quantity = cartEntity.quantity,
            variableQuantity = cartEntity.variableQuantity,
            unit = cartEntity.unit
        )
    }
}
