package com.epam.grocerystoreapp.domain.model.catalog

enum class SortBy(private val requestName: String) {
    PRODUCT_NAME("product_name");

    override fun toString() = requestName
}