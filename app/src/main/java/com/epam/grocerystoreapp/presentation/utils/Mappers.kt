package com.epam.grocerystoreapp.presentation.utils

import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.ProductCategory
import java.lang.Exception

fun getFilterTitleResId(selectedOptionId: Int): Int {
    return when (selectedOptionId) {
        R.id.im_lucky -> R.string.catalog_products_list_filter_im_lucky
        R.id.name_ascent -> R.string.catalog_products_list_filter_name_ascent
        else -> throw Exception("Not valid selected option id: $selectedOptionId")
    }
}

fun getCategoryTitleResId(productCategory: ProductCategory): Int = getCategoryTitleResId(productCategory.name)

fun getCategoryTitleResId(productCategory: String): Int {
    return when (productCategory) {
        ProductCategory.BAKERY.name -> R.string.category_bakery_title
        ProductCategory.MILKY_PRODUCTS.name -> R.string.category_milky_products_title
        ProductCategory.SAUSAGES.name -> R.string.category_sausages_title
        ProductCategory.MEAT.name -> R.string.category_meat_title
        ProductCategory.FISH.name -> R.string.category_fish_title
        ProductCategory.FROZEN_FOOD.name -> R.string.category_frozen_food_title
        ProductCategory.FRUITS_AND_VEGETABLES.name -> R.string.category_fruits_and_vegetables_title
        ProductCategory.SWEETS.name -> R.string.category_sweets_title
        ProductCategory.SNACKS.name -> R.string.category_snacks_title
        ProductCategory.TEA_AND_COFFEE.name -> R.string.category_tea_and_coffee_title
        ProductCategory.DRINKS.name -> R.string.category_drinks_title
        ProductCategory.ALCOHOL.name -> R.string.category_alcohol_title
        ProductCategory.OTHER.name -> R.string.category_other_title
        ProductCategory.ALL.name -> R.string.category_all_title
        else -> R.string.category_default_title
    }
}

fun getCategoryIconResId(productCategory: ProductCategory): Int = getCategoryIconResId(productCategory.name)

fun getCategoryIconResId(productCategory: String): Int {
    return when (productCategory) {
        ProductCategory.BAKERY.name -> R.drawable.ic_category_bakery
        ProductCategory.MILKY_PRODUCTS.name -> R.drawable.ic_category_milk
        ProductCategory.SAUSAGES.name -> R.drawable.ic_category_sausages
        ProductCategory.MEAT.name -> R.drawable.ic_category_meat
        ProductCategory.FISH.name -> R.drawable.ic_category_fish
        ProductCategory.FROZEN_FOOD.name -> R.drawable.ic_category_frozen_food
        ProductCategory.FRUITS_AND_VEGETABLES.name -> R.drawable.ic_category_fruits_and_vegetables
        ProductCategory.SWEETS.name -> R.drawable.ic_category_sweet
        ProductCategory.SNACKS.name -> R.drawable.ic_category_snacks
        ProductCategory.TEA_AND_COFFEE.name -> R.drawable.ic_category_tea_coffee
        ProductCategory.DRINKS.name -> R.drawable.ic_category_drinks
        ProductCategory.ALCOHOL.name -> R.drawable.ic_category_alcohol
        ProductCategory.OTHER.name -> R.drawable.ic_category_other
        ProductCategory.ALL.name -> R.drawable.ic_category_all
        else -> R.drawable.ic_product_preview
    }
}