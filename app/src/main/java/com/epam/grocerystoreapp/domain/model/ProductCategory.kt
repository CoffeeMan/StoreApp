package com.epam.grocerystoreapp.domain.model

enum class ProductCategory {
    ALL,
    OTHER,
    BAKERY,
    MILKY_PRODUCTS,
    SAUSAGES,
    MEAT,
    FISH,
    FROZEN_FOOD,
    FRUITS_AND_VEGETABLES,
    SWEETS,
    SNACKS,
    TEA_AND_COFFEE,
    DRINKS,
    ALCOHOL;

    companion object {
        private val map = values().associateBy(ProductCategory::name)
        fun fromString(type: String?) = if(type == null) null else map[type]
    }
}