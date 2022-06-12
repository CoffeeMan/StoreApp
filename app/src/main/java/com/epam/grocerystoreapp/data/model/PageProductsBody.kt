package com.epam.grocerystoreapp.data.model

import com.squareup.moshi.Json


data class PageProductsBody(
    @Json(name = "count") val count: Int = 0,
    @Json(name ="page") val page: Int = 0,
    @Json(name ="page_count") val page_count: Int = 0,
    @Json(name ="page_size") val page_size: Int = 0,
    @Json(name ="products") val products: List<ProductGeneral> = listOf(),
    @Json(name ="skip") val skip: Int = 0
)