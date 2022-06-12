package com.epam.grocerystoreapp.presentation.screens.catalog

import androidx.lifecycle.*
import androidx.paging.*
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.data.model.ProductGeneral
import com.epam.grocerystoreapp.data.utils.toCatalogItem
import com.epam.grocerystoreapp.domain.model.ProductCategory
import com.epam.grocerystoreapp.domain.model.catalog.FilterBy
import com.epam.grocerystoreapp.domain.model.catalog.SortBy
import com.epam.grocerystoreapp.domain.use_cases.cart.AddCartItemUseCase
import com.epam.grocerystoreapp.domain.use_cases.catalog.GetProductsByCategoryUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.AddFavoriteProductUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.GetAllFavoriteProductsUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.IsProductFavoriteUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.RemoveFavoriteProductUseCase
import com.epam.grocerystoreapp.presentation.model.CatalogProductData
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.ContentPresentationType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductsListViewModel @Inject constructor(
    private val getProductsByCategoryUseCase: GetProductsByCategoryUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val addCartItemUseCase: AddCartItemUseCase,
    private val addFavoriteProductUseCase: AddFavoriteProductUseCase,
    private val removeFavoriteProductUseCase: RemoveFavoriteProductUseCase,
    private val isProductFavoriteUseCase: IsProductFavoriteUseCase,
    private val getAllFavoriteProductsUseCase: GetAllFavoriteProductsUseCase,
) : ViewModel() {

    private val _presentationType = MutableLiveData(ContentPresentationType.GRID)
    val presentationType: LiveData<ContentPresentationType> = _presentationType

    private val _filter = MutableLiveData(R.id.im_lucky)
    val filter: LiveData<Int> = _filter

    private val _sortBy = MutableStateFlow(FilterBy())

    private var _favoriteProducts = mutableListOf<String>()

    init {
        viewModelScope.launch {
            _favoriteProducts.clear()
            _favoriteProducts.addAll(getAllFavoriteProductsUseCase())
        }
    }

    val productsFlow: StateFlow<PagingData<CatalogProductData>> =
        _sortBy.map(::newPager).flatMapLatest { pager ->
            pager.flow.map { productGeneralPagingData ->
                productGeneralPagingData.map { productGeneral ->
                    CatalogProductData(
                        productGeneral.toCatalogItem(_favoriteProducts.contains(productGeneral.id)),
                        hideDiscount = productGeneral.priceWithDiscount == productGeneral.price
                    )
                }
            }
        }
            .cachedIn(viewModelScope)
            .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun newPager(filterBy: FilterBy): Pager<Int, ProductGeneral> {
        return Pager(PagingConfig(PAGE_SIZE, enablePlaceholders = false)) {
            getProductsByCategoryUseCase(
                filterBy,
                ProductCategory.fromString(savedStateHandle.get<String>(Const.CATEGORY_PRODUCTS_KEY))
            )
        }
    }

    fun onSorted(selectedOptionId: Int) {
        _filter.value = selectedOptionId
        _sortBy.tryEmit(_sortBy.value.copy(sortBy = getFilterType(selectedOptionId)))
    }

    fun onSearch(searchText: String) {
        _sortBy.tryEmit(_sortBy.value.copy(searchBy = searchText))
    }

    fun onPresentationTypeChange() {
        _presentationType.value =
            if (_presentationType.value == ContentPresentationType.GRID)
                ContentPresentationType.LIST
            else ContentPresentationType.GRID
    }

    fun addToCart(productId: String) {
        viewModelScope.launch {
            addCartItemUseCase(
                id = productId,
                quantity = 1.0,
                variableQuantity = true,
            )
        }
    }

    fun changeFavorite(productId: String) {
        viewModelScope.launch {
            if(isProductFavoriteUseCase(productId))
                removeFavoriteProduct(productId)
            else
                addFavoriteProduct(productId)
        }
    }

    private suspend fun addFavoriteProduct(productId: String) {
        addFavoriteProductUseCase(productId)
        if(!_favoriteProducts.contains(productId))
            _favoriteProducts.add(productId)
    }

    private suspend fun removeFavoriteProduct(productId: String) {
        removeFavoriteProductUseCase(productId)
        _favoriteProducts.remove(productId)
    }

    private fun getFilterType(filterTypeId: Int?): SortBy? {
        return when (filterTypeId) {
            R.id.name_ascent -> SortBy.PRODUCT_NAME
            else -> null
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}