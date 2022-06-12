package com.epam.grocerystoreapp.domain.use_cases.stores

import com.epam.grocerystoreapp.domain.model.Store
import com.epam.grocerystoreapp.domain.repository.StoresRepository
import javax.inject.Inject

class GetStoreByIdUseCase @Inject constructor(
    private val repository: StoresRepository,
) {

    suspend operator fun invoke(storeId: String): Store? {
        return repository.getStoreById(storeId)
    }
}
