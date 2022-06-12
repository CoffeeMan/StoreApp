package com.epam.grocerystoreapp.domain.use_cases.product

import com.epam.grocerystoreapp.domain.model.Product
import com.epam.grocerystoreapp.domain.repository.ProductRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductRepository,
) {

    suspend operator fun invoke(productId: String): Product {
        return repository.getProductById(productId)
    }

}
