package com.epam.grocerystoreapp.domain.use_cases.stores

import com.epam.grocerystoreapp.domain.model.Store
import com.epam.grocerystoreapp.domain.repository.StoresRepository
import javax.inject.Inject

class GetAllStoresUseCase @Inject constructor(
    private val repository: StoresRepository,
) {

    suspend operator fun invoke(): List<Store> {
        return repository.getAllStores()
    }
}