package com.epam.grocerystoreapp.domain.model

enum class ProductUnit(val discreteValue: Double) {
    PIECES(1.0),
    KG(0.100),
    GRAM_100(100.0),
    LITERS(0.5),
}
