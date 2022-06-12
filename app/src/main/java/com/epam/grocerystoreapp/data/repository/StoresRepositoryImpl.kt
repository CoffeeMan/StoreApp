package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.domain.model.Store
import com.epam.grocerystoreapp.domain.repository.StoresRepository
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class StoresRepositoryImpl @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
) : StoresRepository {

    override suspend fun getAllStores(): List<Store> {
        return firebaseDatabase.getReference(DATABASE_SHOPS_PATH)
            .get()
            .await()
            .getValue<List<Store?>>()
            ?.filterNotNull()
            ?: emptyList()
    }

    override suspend fun getStoreById(storeId: String): Store? {
        return firebaseDatabase.getReference(DATABASE_SHOPS_PATH)
            .child(storeId)
            .get()
            .await()
            .getValue<Store?>()
    }

    companion object {
        private const val DATABASE_SHOPS_PATH = "shops"
    }

}
