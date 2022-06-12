package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.Store

interface StoresRepository {
    suspend fun getAllStores(): List<Store>
    suspend fun getStoreById(storeId: String): Store?
}
