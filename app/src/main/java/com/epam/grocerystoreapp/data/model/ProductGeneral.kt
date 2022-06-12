package com.epam.grocerystoreapp.data.model

import com.squareup.moshi.Json


data class ProductGeneral(
    @Json(name = "code") val id: String = "",
    @Json(name = "product_name") val name: String = "",
    @Json(name = "categories") val category: String = "",
    @Json(name = "brands") val price: Double = 0.0,
    @Json(name = "packaging") val priceWithDiscount: Double = 0.0,
    @Json(name = "ingredients_text") val description: String = "",
    @Json(name = "image_url") val photoUrl: String = "",
    @Json(name = "emb_codes") val unit: String = ""
)
