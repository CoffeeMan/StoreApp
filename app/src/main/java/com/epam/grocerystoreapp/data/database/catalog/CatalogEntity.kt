package com.epam.grocerystoreapp.data.database.catalog

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.epam.grocerystoreapp.domain.model.ProductCategory
import com.epam.grocerystoreapp.domain.model.ProductUnit

@Entity(tableName = "catalog_entities")
data class CatalogEntity(
    @PrimaryKey @ColumnInfo(name = "catalog_entity_id") val id: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "category") val category: ProductCategory,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "photo_url") val photoUrl: String,
    @ColumnInfo(name = "discount") val priceWithDiscount: Double,
    @ColumnInfo(name = "is_favorite") val isFavorite: Boolean,
    @ColumnInfo(name = "unit") val unit: ProductUnit,
)
