package com.epam.grocerystoreapp.domain.model.home

sealed class HomeItem(open val id: String) {

    data class PromotionItem(
        override val id: String = "",
        val title: String = "",
        val imageUrl: String = "",
        val description: String = ""
    ) : HomeItem(id)

    data class LoyaltyCardItem(
        override val id: String = "",
        val title: String = "",
        val imageUrl: String = "",
        val description: String = ""
    ) : HomeItem(id)

    data class NewsItem(
        override val id: String = "",
        val title: String = "",
        val imageUrl: String = "",
        val description: String = ""
    ) : HomeItem(id)
}