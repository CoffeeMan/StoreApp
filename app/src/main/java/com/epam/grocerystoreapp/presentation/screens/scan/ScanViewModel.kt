package com.epam.grocerystoreapp.presentation.screens.scan

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.domain.model.CatalogItem
import com.epam.grocerystoreapp.domain.use_cases.cart.AddCartItemUseCase
import com.epam.grocerystoreapp.domain.use_cases.scan.GetProductByBarcodeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val getProductByBarcodeUseCase: GetProductByBarcodeUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,
) : ViewModel() {

    private val _productInfo = MutableLiveData<CatalogItem?>()
    val productInfo: MutableLiveData<CatalogItem?> = _productInfo

    fun getProductByBarcode(barcode: String) {
        viewModelScope.launch {
            _productInfo.value =
                withContext(Dispatchers.IO) { getProductByBarcodeUseCase(barcode = barcode) }
        }
    }

    fun addScannedProductToCart() {
        _productInfo.value?.let { productInfo ->
            viewModelScope.launch {
                /** Добавить логику определения variableQuantity */
                val variableQuantity = true

                addCartItemUseCase(
                    id = productInfo.id,
                    quantity = 1.0,
                    variableQuantity = variableQuantity
                )
            }
        }
    }
}
