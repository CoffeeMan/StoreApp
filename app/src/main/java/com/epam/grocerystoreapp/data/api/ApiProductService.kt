package com.epam.grocerystoreapp.data.api

import androidx.annotation.IntRange
import com.epam.grocerystoreapp.data.model.PageProductsBody
import com.epam.grocerystoreapp.domain.model.catalog.SortBy
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiProductService {
    @GET("/cgi/search.pl?action=process&tagtype_0=categories&tag_contains_0=contains&tagtype_1=countries&tag_contains_1=contains&tag_1=nyl&json=true&fields=image_url,product_name,categories,code,packaging,brands,ingredients_text,emb_codes")
    suspend fun getProductsList(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("page_size") @IntRange(
            from = 1,
            to = MAX_PAGE_SIZE.toLong()
        ) pageSize: Int = DEFAULT_PAGE_SIZE,
        @Query("sort_by") sortBy: SortBy? = null,
        @Query("tag_0") category: String? = null,
        @Query("search_terms") searchBy: String? = null,
    ): PageProductsBody

    @GET("/country/nyl/all_products.json?json=true&fields=image_url,product_name,categories,code,packaging,brands,ingredients_text,emb_codes")
    suspend fun getProductsList(): PageProductsBody

    @GET("/?json=true&fields=image_url,product_name,categories,code,packaging,brands,ingredients_text,emb_codes")
    suspend fun getProductByCode(@Query("code") code: String): PageProductsBody

    companion object {
        const val DEFAULT_PAGE_SIZE = 20
        const val MAX_PAGE_SIZE = 20
    }
}