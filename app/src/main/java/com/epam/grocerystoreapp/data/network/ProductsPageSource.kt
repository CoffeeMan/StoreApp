package com.epam.grocerystoreapp.data.network

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.epam.grocerystoreapp.data.api.ApiProductService
import com.epam.grocerystoreapp.data.model.ProductGeneral
import com.epam.grocerystoreapp.domain.model.ProductCategory
import com.epam.grocerystoreapp.domain.model.catalog.FilterBy
import com.epam.grocerystoreapp.domain.model.catalog.SortBy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.lang.Exception

class ProductsPageSource(
    private val productService: ApiProductService,
    private val filterBy: FilterBy,
    private val category: ProductCategory?
) : PagingSource<Int, ProductGeneral>() {
    override fun getRefreshKey(state: PagingState<Int, ProductGeneral>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ProductGeneral> {
        return try {
            val page = params.key ?: 1
            val pageSize = params.loadSize
            withContext(Dispatchers.IO) {
                val result =
                    productService.getProductsList(
                        page = page,
                        pageSize = pageSize,
                        sortBy = filterBy.sortBy,
                        category = category?.name,
                        searchBy = filterBy.searchBy
                    )
                val products = result.products
                val nextKey = if (products.size < pageSize) null else page + 1
                val prevKey = if (page == 1) null else page - 1
                LoadResult.Page(products, prevKey = prevKey, nextKey = nextKey)
            }
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }
}