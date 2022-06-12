package com.epam.grocerystoreapp.presentation.screens.auth.sign_in

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.auth.SignInUseCase
import com.epam.grocerystoreapp.presentation.utils.Const.APP_LOG_TAG
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidEmail
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidPassword
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.google.firebase.FirebaseNetworkException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val navigationIdSingleEvent = SingleLiveEvent<Int>()
    fun navigationId() = navigationIdSingleEvent

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    fun onSignInClicked(email: String, password: String) {
        showProgress()

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            when (exception) {
                is FirebaseNetworkException -> {
                    toastResIdSingleEvent.value = R.string.hint_no_internet_connection
                }
                else -> {
                    toastResIdSingleEvent.value = R.string.auth_hint_wrong_email_or_password
                }
            }
            Log.e(APP_LOG_TAG, exception.toString())
            hideProgress()
        }

        viewModelScope.launch(exceptionHandler) {
            if (!isValidEmail(email)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_email
            } else if (!isValidPassword(password)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_password
            } else {
                withContext(Dispatchers.IO) {
                    signInUseCase(
                        email = email,
                        password = password
                    )
                }

                navigationIdSingleEvent.value = R.id.action_signInFragment_to_homeFragment
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
