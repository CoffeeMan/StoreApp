package com.epam.grocerystoreapp.presentation.screens.auth.restore_password

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.auth.RestorePasswordUseCase
import com.epam.grocerystoreapp.presentation.utils.Const.APP_LOG_TAG
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidEmail
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.google.firebase.FirebaseNetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RestorePasswordViewModel @Inject constructor(
    private val restorePasswordUseCase: RestorePasswordUseCase,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val navigationIdSingleEvent = SingleLiveEvent<Int>()
    fun navigationId() = navigationIdSingleEvent

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    fun onRestorePasswordClicked(email: String) {
        showProgress()

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            when (exception) {
                is FirebaseNetworkException -> {
                    toastResIdSingleEvent.value = R.string.hint_no_internet_connection
                }
                else -> {
                    toastResIdSingleEvent.value = R.string.auth_hint_email_did_not_found
                }
            }
            Log.e(APP_LOG_TAG, exception.toString())
            hideProgress()
        }

        viewModelScope.launch(exceptionHandler) {
            if (!isValidEmail(email)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_email
                hideProgress()
            } else {
                withContext(Dispatchers.IO) {
                    restorePasswordUseCase(email = email)
                }

                toastResIdSingleEvent.value = R.string.restore_password_hint_check_mail
                navigationIdSingleEvent.value =
                    R.id.action_restorePasswordFragment_to_signInFragment
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
