package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.domain.repository.FavoriteProductsRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FavoriteProductsRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase,
) : FavoriteProductsRepository {

    override suspend fun getAllFavoriteProducts(): List<String> {
        return firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseDatabase.getReference(DATABASE_FAVORITE_PRODUCTS_PATH)
                .child(uid)
                .get()
                .await()
                .getValue<HashMap<String, Boolean>>()
                ?.keys
                ?.toList()
                ?: emptyList()
        } ?: emptyList()
    }

    override suspend fun addFavoriteProduct(productId: String) {
        firebaseAuth.currentUser?.uid?.let { uid ->
            if (!isProductFavorite(productId)) {
                firebaseDatabase.getReference(DATABASE_FAVORITE_PRODUCTS_PATH)
                    .child(uid)
                    .child(productId)
                    .setValue(true)
                    .await()
            }
        }
    }

    override suspend fun removeFavoriteProduct(productId: String) {
        firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseDatabase.getReference(DATABASE_FAVORITE_PRODUCTS_PATH)
                .child(uid)
                .child(productId)
                .removeValue()
                .await()
        }
    }

    override suspend fun isProductFavorite(productId: String): Boolean {
        return firebaseAuth.currentUser?.uid?.let { uid ->
            firebaseDatabase.getReference(DATABASE_FAVORITE_PRODUCTS_PATH)
                .child(uid)
                .child(productId)
                .get()
                .await()
                .exists()
        } ?: false
    }

    companion object {
        private const val DATABASE_FAVORITE_PRODUCTS_PATH = "favorite_products"
    }

}