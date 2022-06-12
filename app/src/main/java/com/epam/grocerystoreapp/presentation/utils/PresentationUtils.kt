package com.epam.grocerystoreapp.presentation.utils

import android.content.res.Resources
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.ProductUnit
import java.text.DecimalFormat
import java.util.*

object PresentationUtils {

    private const val PASSWORD_MIN_LENGTH = 6
    private const val PASSWORD_MAX_LENGTH = 20
    private const val PASSWORD_REGEX = "[0-9a-zA-Z]{$PASSWORD_MIN_LENGTH,$PASSWORD_MAX_LENGTH}"

    private const val EMAIL_MIN_LENGTH = 6

    private const val NAME_MIN_LENGTH = 2
    private const val NAME_MAX_LENGTH = 20
    private const val NAME_REGEX = "[Ёёа-яА-Яa-zA-Z]{$NAME_MIN_LENGTH,$NAME_MAX_LENGTH}"

    fun isValidEmail(email: String): Boolean {
        return !TextUtils.isEmpty(email)
                && Patterns.EMAIL_ADDRESS.matcher(email).matches()
                && email.length >= EMAIL_MIN_LENGTH
    }

    fun isValidPassword(password: String): Boolean {
        return password.matches(Regex(PASSWORD_REGEX))
    }

    fun isValidName(name: String): Boolean {
        return name.matches(Regex(NAME_REGEX))
    }

    fun getCapitalizedString(name: String): String {
        return name.replaceFirstChar { it.titlecase(Locale.getDefault()) }
    }

    fun priceDoubleToString(price: Double): String {
        return DecimalFormat("#,##0.00").format(price).plus(" ₽")
    }

    fun quantityDoubleToString(quantity: Double, unit: ProductUnit, resources: Resources): String {
        return when (unit) {
            ProductUnit.PIECES -> DecimalFormat("#,##0").format(quantity)
            ProductUnit.KG -> DecimalFormat("#,##0.000").format(quantity)
            ProductUnit.GRAM_100 -> DecimalFormat("#,##0").format(quantity)
            ProductUnit.LITERS -> DecimalFormat("#,##0.0").format(quantity)
        }.plus(" ${productUnitToString(unit = unit, resources = resources)}")
    }

    fun productUnitStandardQuantityToString(unit: ProductUnit, resources: Resources): String {
        return when (unit) {
            ProductUnit.PIECES -> resources.getString(R.string.product_unit_pieces_standard_quantity)
            ProductUnit.KG -> resources.getString(R.string.product_unit_kg_standard_quantity)
            ProductUnit.GRAM_100 -> resources.getString(R.string.product_unit_gram_100_standard_quantity)
            ProductUnit.LITERS -> resources.getString(R.string.product_unit_liters_standard_quantity)
        }
    }

    private fun productUnitToString(unit: ProductUnit, resources: Resources): String {
        return when (unit) {
            ProductUnit.PIECES -> resources.getString(R.string.product_unit_pieces)
            ProductUnit.KG -> resources.getString(R.string.product_unit_kg)
            ProductUnit.GRAM_100 -> resources.getString(R.string.product_unit_gram_100)
            ProductUnit.LITERS -> resources.getString(R.string.product_unit_liters)
        }
    }

    fun getVisibilityByBoolean(isVisible: Boolean): Int {
        return if (isVisible) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

}
