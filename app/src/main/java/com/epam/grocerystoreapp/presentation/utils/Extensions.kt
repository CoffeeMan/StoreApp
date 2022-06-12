package com.epam.grocerystoreapp.presentation.utils

fun Double.convertToRubels() : String = "$this RUB"

fun Double.getPriceWithDiscount(discount: Double) : Double = this * (1.0 - (discount / 100.0))