package com.epam.grocerystoreapp.presentation.screens.stores

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.Store
import com.epam.grocerystoreapp.domain.use_cases.stores.GetAllStoresUseCase
import com.epam.grocerystoreapp.presentation.utils.Const.APP_LOG_TAG
import com.epam.grocerystoreapp.presentation.utils.Const.NO_INTERNET_EXCEPTION_MESSAGE
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.google.android.gms.maps.model.Marker
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("NullSafeMutableLiveData")
@HiltViewModel
open class StoresViewModel @Inject constructor(
    private val getAllStoresUseCase: GetAllStoresUseCase,
) : ViewModel() {

    private val _stores = MutableLiveData<List<Store>>()
    val stores: LiveData<List<Store>> = _stores

    private val storeIdSingleEvent = SingleLiveEvent<String>()
    fun storeId() = storeIdSingleEvent

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    init {
        loadStores()
    }

    fun onMarkerClicked(marker: Marker) {
        val storesList = _stores.value

        val store = storesList?.find { store ->
            store.latitude == marker.position.latitude && store.longitude == marker.position.longitude
        }

        store?.let {
            storeIdSingleEvent.value = it.id
        }
    }

    private fun loadStores() {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            when (exception.message) {
                NO_INTERNET_EXCEPTION_MESSAGE -> {
                    toastResIdSingleEvent.value = R.string.hint_no_internet_connection
                }
                else -> {
                    toastResIdSingleEvent.value = R.string.something_went_wrong
                }
            }
            Log.e(APP_LOG_TAG, exception.toString())
        }

        viewModelScope.launch(exceptionHandler) {
            _stores.value = withContext(Dispatchers.IO) {
                getAllStoresUseCase()
            }
        }
    }

}
