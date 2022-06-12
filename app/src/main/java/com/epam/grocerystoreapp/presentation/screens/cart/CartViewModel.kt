package com.epam.grocerystoreapp.presentation.screens.cart

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.cart.*
import com.epam.grocerystoreapp.presentation.model.CartItemUi
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.Const.API_EXCEPTION_MESSAGE
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.epam.grocerystoreapp.presentation.utils.priceDoubleToString
import com.epam.grocerystoreapp.presentation.utils.toCartItemsUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getAllCartItemsUseCase: GetAllCartItemsUseCase,
    private val deleteCartItemUseCase: DeleteCartItemUseCase,
    private val increaseCartItemUseCase: IncreaseCartItemUseCase,
    private val decreaseCartItemUseCase: DecreaseCartItemUseCase,
    private val clearCartUseCase: ClearCartUseCase,
    private val getCartCostUseCase: GetCartCostUseCase,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val _cartItemsUi = MutableLiveData<List<CartItemUi>>()
    val cartItemsUi: LiveData<List<CartItemUi>> = _cartItemsUi

    private val _cartCost = MutableLiveData<String>()
    val cartCost: LiveData<String> = _cartCost

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

    fun onViewCreated() {
        viewModelScope.launch(exceptionHandler) {
            showProgress()
            updateFields()
            hideProgress()
        }
    }

    fun onDeleteClicked(cartItemUi: CartItemUi) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                deleteCartItemUseCase(cartItemUi.id)
            }
            updateFields()
        }
    }

    fun onPlusClicked(cartItemUi: CartItemUi) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                increaseCartItemUseCase(cartItemUi.id, cartItemUi.unit)
            }
            updateFields()
        }
    }

    fun onMinusClicked(cartItemUi: CartItemUi) {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                when (cartItemUi.quantity) {
                    in Double.MIN_VALUE..cartItemUi.unit.discreteValue -> {
                        deleteCartItemUseCase(cartItemUi.id)
                    }
                    else -> {
                        decreaseCartItemUseCase(cartItemUi.id, cartItemUi.unit)
                    }
                }
            }
            updateFields()
        }
    }

    fun onClearCartClicked() {
        viewModelScope.launch(exceptionHandler) {
            withContext(Dispatchers.IO) {
                clearCartUseCase()
            }
            updateFields()
        }
    }

    fun onProductFragmentResult(isQuantityChanged: Boolean) {
        if (isQuantityChanged) {
            viewModelScope.launch(exceptionHandler) {
                updateFields()
            }
        }
    }

    private suspend fun updateFields() {
        val cartItemsUi = withContext(Dispatchers.IO) {
            getCartItems()
        }
        val cartCost = withContext(Dispatchers.IO) {
            getCartCost()
        }
        _cartItemsUi.value = cartItemsUi
        _cartCost.value = cartCost
    }

    private suspend fun getCartItems(): List<CartItemUi> {
        return withContext(Dispatchers.IO) {
            getAllCartItemsUseCase().toCartItemsUi()
        }
    }

    private suspend fun getCartCost(): String {
        return getCartCostUseCase().priceDoubleToString()
    }

    private fun showProgress() {
        _progressBarVisibility.value = View.VISIBLE
    }

    private fun hideProgress() {
        _progressBarVisibility.value = View.GONE
    }

}
