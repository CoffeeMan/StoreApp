package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.home.HomeItem

interface HomeRepository {
    suspend fun getPromoItems(): List<HomeItem.PromotionItem>
    suspend fun getNewsItems(): List<HomeItem.NewsItem>
}
