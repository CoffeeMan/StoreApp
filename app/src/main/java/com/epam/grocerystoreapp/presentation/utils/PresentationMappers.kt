package com.epam.grocerystoreapp.presentation.utils

import android.content.res.Resources
import com.epam.grocerystoreapp.domain.model.CartItem
import com.epam.grocerystoreapp.domain.model.Product
import com.epam.grocerystoreapp.domain.model.User
import com.epam.grocerystoreapp.presentation.model.CartItemUi
import com.epam.grocerystoreapp.presentation.model.ProductUi
import com.epam.grocerystoreapp.presentation.model.UserUi
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDateStringByTimestamp
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.getVisibilityByBoolean
import java.text.DecimalFormat

fun Double.priceDoubleToString(): String {
    return DecimalFormat("#,##0.00").format(this).plus(" ₽")
}

fun List<CartItem>.toCartItemsUi(): List<CartItemUi> {
    return this.map { cartItem ->
        CartItemUi(
            id = cartItem.id,
            name = cartItem.name,
            photoUrl = cartItem.photoUrl,
            price = cartItem.price,
            priceWithDiscount = cartItem.priceWithDiscount,
            priceWithDiscountVisibility = getVisibilityByBoolean(
                cartItem.price != cartItem.priceWithDiscount
            ),
            quantity = cartItem.quantity,
            variableQuantity = cartItem.variableQuantity,
            unit = cartItem.unit
        )
    }
}

fun User.toUserUi(resources: Resources): UserUi {
    return UserUi(
        id = this.id,
        name = this.name,
        surname = this.surname,
        dateOfBirth = getDateStringByTimestamp(
            resources = resources,
            date = this.dateOfBirth,
        ),
        email = this.email
    )
}

fun Product.toProductUi(): ProductUi {
    return ProductUi(
        id = this.id,
        name = this.name,
        description = this.description,
        photoUrl = this.photoUrl,
        priceWithDiscount = PresentationUtils.priceDoubleToString(this.priceWithDiscount),
        priceWithoutDiscount = PresentationUtils.priceDoubleToString(this.priceWithoutDiscount),
        priceWithoutDiscountVisibility = getVisibilityByBoolean(
            this.priceWithDiscount != this.priceWithoutDiscount
        ),
        isFavorite = this.isFavorite,
        unit = this.unit
    )
}
