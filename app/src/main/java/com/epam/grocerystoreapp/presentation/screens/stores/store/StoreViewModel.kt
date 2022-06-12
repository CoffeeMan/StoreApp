package com.epam.grocerystoreapp.presentation.screens.stores.store

import android.annotation.SuppressLint
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.Store
import com.epam.grocerystoreapp.domain.use_cases.stores.GetStoreByIdUseCase
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.Const.NO_INTERNET_EXCEPTION_MESSAGE
import com.epam.grocerystoreapp.presentation.utils.Const.STORE_ID_KEY
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@SuppressLint("NullSafeMutableLiveData")
@HiltViewModel
class StoreViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    getStoreByIdUseCase: GetStoreByIdUseCase,
) : ViewModel() {

    private val _store = MutableLiveData<Store>()
    val store: LiveData<Store> = _store

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    init {
        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            when (exception.message) {
                NO_INTERNET_EXCEPTION_MESSAGE -> {
                    toastResIdSingleEvent.value = R.string.hint_no_internet_connection
                }
                else -> {
                    toastResIdSingleEvent.value = R.string.something_went_wrong
                }
            }
            Log.e(Const.APP_LOG_TAG, exception.toString())
            hideProgress()
        }

        viewModelScope.launch(exceptionHandler) {
            showProgress()

            savedStateHandle.get<String>(STORE_ID_KEY)?.let { storeId ->
                _store.value = withContext(Dispatchers.IO) {
                    getStoreByIdUseCase(storeId)
                }
            }

            hideProgress()
        }
    }

    private fun showProgress() {
        _progressBarVisibility.value = View.VISIBLE
    }

    private fun hideProgress() {
        _progressBarVisibility.value = View.GONE
    }

}
