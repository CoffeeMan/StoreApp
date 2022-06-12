package com.epam.grocerystoreapp.data.database.cart

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cart_entities")
data class CartEntity(
    @PrimaryKey @ColumnInfo(name = "cart_entity_id") val id: String,
    @ColumnInfo(name = "quantity") val quantity: Double,
    @ColumnInfo(name = "variable_quantity") val variableQuantity: Boolean,
)
