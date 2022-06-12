package com.epam.grocerystoreapp.presentation.screens.profile

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.auth.GetUserUseCase
import com.epam.grocerystoreapp.domain.use_cases.auth.SignOutUseCase
import com.epam.grocerystoreapp.presentation.model.UserUi
import com.epam.grocerystoreapp.presentation.utils.Const.APP_LOG_TAG
import com.epam.grocerystoreapp.presentation.utils.Const.NO_INTERNET_EXCEPTION_MESSAGE
import com.epam.grocerystoreapp.presentation.utils.ResourceManager
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.epam.grocerystoreapp.presentation.utils.toUserUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserUseCase: GetUserUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val navigationIdSingleEvent = SingleLiveEvent<Int>()
    fun navigationId() = navigationIdSingleEvent

    private val _userUi = MutableLiveData<UserUi>()
    val userUi: LiveData<UserUi> = _userUi

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        when (exception.message) {
            NO_INTERNET_EXCEPTION_MESSAGE -> {
                toastResIdSingleEvent.value = R.string.hint_no_internet_connection
            }
            else -> {
                toastResIdSingleEvent.value = R.string.something_went_wrong
            }
        }
        Log.e(APP_LOG_TAG, exception.toString())
        hideProgress()
    }

    fun onViewCreated() {
        getUser()
    }

    fun onSignOutClicked() {
        if (_userUi.value != null) {
            viewModelScope.launch(exceptionHandler) {
                showProgress()

                withContext(Dispatchers.IO) {
                    signOutUseCase()
                }

                navigationIdSingleEvent.value = R.id.action_profileFragment_to_startAuthFragment
                hideProgress()
            }
        } else {
            toastResIdSingleEvent.value = R.string.auth_hint_get_user_unsuccessful
        }
    }

    fun onEditProfileClicked() {
        if (_userUi.value != null) {
            navigationIdSingleEvent.value = R.id.action_profileFragment_to_profileEditFragment
        } else {
            toastResIdSingleEvent.value = R.string.auth_hint_get_user_unsuccessful
        }
    }

    private fun getUser() {
        viewModelScope.launch(exceptionHandler) {
            showProgress()

            val userUi = withContext(Dispatchers.IO) {
                getUserUseCase().toUserUi(resources = resourceManager.getResources())
            }

            _userUi.value = userUi
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
