package com.epam.grocerystoreapp.presentation.screens.product

import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.ProductUnit
import com.epam.grocerystoreapp.domain.use_cases.cart.AddCartItemUseCase
import com.epam.grocerystoreapp.domain.use_cases.cart.GetProductQuantityInCartUseCase
import com.epam.grocerystoreapp.domain.use_cases.product.GetProductByIdUseCase
import com.epam.grocerystoreapp.presentation.model.ProductUi
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.Const.API_EXCEPTION_MESSAGE
import com.epam.grocerystoreapp.presentation.utils.Const.PRODUCT_ID_KEY
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.quantityDoubleToString
import com.epam.grocerystoreapp.presentation.utils.ResourceManager
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.epam.grocerystoreapp.presentation.utils.toProductUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getProductByIdUseCase: GetProductByIdUseCase,
    private val addCartItemUseCase: AddCartItemUseCase,
    private val getProductQuantityInCartUseCase: GetProductQuantityInCartUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val _productUi = MutableLiveData<ProductUi>()
    val productUi: LiveData<ProductUi> = _productUi

    private val _quantityString = MutableLiveData<String>()
    val quantityString: LiveData<String> = _quantityString

    private val _isMinusBtnEnabled = MutableLiveData<Boolean>(false)
    val isMinusBtnEnabled: LiveData<Boolean> = _isMinusBtnEnabled

    private val _isQuantityChanged = MutableLiveData<Boolean>()
    val isQuantityChanged: LiveData<Boolean> = _isQuantityChanged

    private val _quantityInCart = MutableLiveData<String>()
    val quantityInCart: LiveData<String> = _quantityInCart

    private var quantity: Double = 0.0
    private var unit: ProductUnit? = null

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception.message) {
            API_EXCEPTION_MESSAGE -> {
                toastResIdSingleEvent.value = R.string.hint_no_internet_connection
            }
            else -> {
                toastResIdSingleEvent.value = R.string.something_went_wrong
            }
        }
        Log.e(Const.APP_LOG_TAG, exception.toString())
        hideProgress()
    }

    init {
        getProduct()
    }

    fun onAddToCartClicked() {
        if (_productUi.value != null) {
            viewModelScope.launch(exceptionHandler) {
                showProgress()
                savedStateHandle.get<String>(PRODUCT_ID_KEY)?.let { productId ->
                    addCartItemUseCase(
                        id = productId,
                        quantity = quantity,
                        variableQuantity = true,
                    )
                }
                _isQuantityChanged.value = true
                checkQuantityInCart()
                hideProgress()
            }
        } else {
            toastResIdSingleEvent.value = R.string.get_data_unsuccessful
        }
    }

    fun onPlusBtnClicked() {
        unit?.let { productUnit ->
            quantity += productUnit.discreteValue
            updateQuantityString()
        }
    }

    fun onMinusBtnClicked() {
        unit?.let { productUnit ->
            quantity -= productUnit.discreteValue
            updateQuantityString()
        }
    }

    private fun getProduct() {
        viewModelScope.launch(exceptionHandler) {
            showProgress()
            savedStateHandle.get<String>(PRODUCT_ID_KEY)?.let { productId ->
                val product = getProductByIdUseCase(productId)
                val productUi = product.toProductUi()
                unit = product.unit
                _productUi.value = productUi
                checkQuantityInCart()
            }
            hideProgress()
        }
    }

    private fun updateQuantityString() {
        unit?.let { productUnit ->
            _quantityString.value = quantityDoubleToString(
                quantity = quantity,
                unit = productUnit,
                resources = resourceManager.getResources()
            )
            _isMinusBtnEnabled.value = quantity >= productUnit.discreteValue * 2
        }
    }

    private suspend fun checkQuantityInCart() {
        savedStateHandle.get<String>(PRODUCT_ID_KEY)?.let { productId ->
            val quantityInCart = getProductQuantityInCartUseCase(productId)

            quantityInCart?.let { quantityInCartDouble ->
                unit?.let { productUnit ->
                    val quantityString = quantityDoubleToString(
                        quantity = quantityInCartDouble,
                        unit = productUnit,
                        resources = resourceManager.getResources(),
                    )

                    _quantityInCart.value = String.format(
                        resourceManager.getString(R.string.product_quantity_in_cart),
                        quantityString,
                    )
                }
            }

            if (quantity == 0.0) {
                unit?.let { productUnit ->
                    quantity = quantityInCart ?: productUnit.discreteValue
                    updateQuantityString()
                }
            }
        }
    }

    private fun showProgress() {
        _progressBarVisibility.value = View.VISIBLE
    }

    private fun hideProgress() {
        _progressBarVisibility.value = View.GONE
    }

}
