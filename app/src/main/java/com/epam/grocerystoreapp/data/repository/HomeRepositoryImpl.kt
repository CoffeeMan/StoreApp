package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.domain.model.home.HomeItem
import com.epam.grocerystoreapp.domain.repository.HomeRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class HomeRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
) : HomeRepository {

    override suspend fun getPromoItems(): List<HomeItem.PromotionItem> {
        return firebaseDatabase.getReference(DATABASE_PROMO_PATH)
            .get()
            .await()
            .getValue<HashMap<String, HomeItem.PromotionItem>>()
            ?.values
            ?.toList()
            ?: emptyList()
    }

    override suspend fun getNewsItems(): List<HomeItem.NewsItem> {
        return firebaseDatabase.getReference(DATABASE_NEWS_PATH)
            .get()
            .await()
            .getValue<HashMap<String, HomeItem.NewsItem>>()
            ?.values
            ?.toList()
            ?: emptyList()
    }

    companion object {
        private const val DATABASE_NEWS_PATH = "news"
        private const val DATABASE_PROMO_PATH = "promo"
    }

}