package com.epam.grocerystoreapp.data.database.cart

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.epam.grocerystoreapp.domain.model.ProductUnit

data class CartDbItem(
    @PrimaryKey @ColumnInfo(name = "cart_entity_id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "discount") val discount: Double,
    @ColumnInfo(name = "quantity") val quantity: Double,
    @ColumnInfo(name = "variable_quantity") val variableQuantity: Boolean,
    @ColumnInfo(name = "unit") val unit: ProductUnit,
)
