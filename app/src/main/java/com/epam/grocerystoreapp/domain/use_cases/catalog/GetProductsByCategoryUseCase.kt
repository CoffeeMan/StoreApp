package com.epam.grocerystoreapp.domain.use_cases.catalog

import androidx.paging.PagingSource
import com.epam.grocerystoreapp.data.model.ProductGeneral
import com.epam.grocerystoreapp.domain.model.ProductCategory
import com.epam.grocerystoreapp.domain.model.catalog.FilterBy
import com.epam.grocerystoreapp.domain.model.catalog.SortBy
import com.epam.grocerystoreapp.domain.repository.CatalogRepository
import javax.inject.Inject

class GetProductsByCategoryUseCase @Inject constructor(
    private val repository: CatalogRepository,
) {

    operator fun invoke(filterBy: FilterBy, category: ProductCategory?): PagingSource<Int, ProductGeneral> {
        return repository.getProductsByCategory(filterBy, category)
    }

}