package com.epam.grocerystoreapp.presentation.screens.auth.sign_up

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.auth.SignUpUseCase
import com.epam.grocerystoreapp.presentation.model.DatePickerData
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDateStringByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDayIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getMonthIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getTimestampGmtByInt
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getYearIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.Const
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.getCapitalizedString
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidEmail
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidName
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidPassword
import com.epam.grocerystoreapp.presentation.utils.ResourceManager
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpUseCase: SignUpUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val _datePickerData = MutableLiveData<DatePickerData>(null)
    val datePickerData: LiveData<DatePickerData> = _datePickerData

    private val _dateOfBirth = MutableLiveData<String>(null)
    val dateOfBirth: LiveData<String> = _dateOfBirth

    private var dateOfBirthTimestamp = INITIAL_DATE_OF_BIRTH

    private val navigationIdSingleEvent = SingleLiveEvent<Int>()
    fun navigationId() = navigationIdSingleEvent

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    fun onSignUpBtnClicked(
        name: String,
        surname: String,
        dateOfBirth: String,
        email: String,
        password: String,
        repeatPassword: String,
    ) {
        showProgress()

        val exceptionHandler = CoroutineExceptionHandler { _, exception ->
            when (exception) {
                is FirebaseAuthUserCollisionException -> {
                    toastResIdSingleEvent.value = R.string.auth_hint_email_exists
                }
                is FirebaseNetworkException -> {
                    toastResIdSingleEvent.value = R.string.hint_no_internet_connection
                }
                else -> {
                    toastResIdSingleEvent.value = R.string.auth_hint_sign_up_unsuccessful
                }
            }
            Log.e(Const.APP_LOG_TAG, exception.toString())
            hideProgress()
        }

        viewModelScope.launch(exceptionHandler) {
            if (!isValidName(name)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_name
            } else if (!isValidName(surname)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_surname
            } else if (dateOfBirth.isBlank()) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_date_of_birth
            } else if (!isValidEmail(email)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_email
            } else if (!isValidPassword(password)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_password
            } else if (password != repeatPassword) {
                toastResIdSingleEvent.value = R.string.auth_hint_passwords_not_match
            } else {
                val capitalizedName = getCapitalizedString(name)
                val capitalizedSurname = getCapitalizedString(surname)

                withContext(Dispatchers.IO) {
                    signUpUseCase(
                        name = capitalizedName,
                        surname = capitalizedSurname,
                        dateOfBirth = dateOfBirthTimestamp,
                        email = email,
                        password = password
                    )
                }

                hideProgress()
                navigationIdSingleEvent.value = R.id.action_signUpFragment_to_signInFragment
            }
            hideProgress()
        }
    }

    fun onDateOfBirthClicked() {
        val year = getYearIntCurrentTimezoneByTimestamp(dateOfBirthTimestamp)
        val month = getMonthIntCurrentTimezoneByTimestamp(dateOfBirthTimestamp)
        val day = getDayIntCurrentTimezoneByTimestamp(dateOfBirthTimestamp)

        val datePickerData = DatePickerData(year, month - 1, day)
        _datePickerData.value = datePickerData
    }

    fun onDateOfBirthPicked(year: Int, month: Int, dayOfMonth: Int) {
        dateOfBirthTimestamp = getTimestampGmtByInt(year, month, dayOfMonth, 0, 0)
        _dateOfBirth.value = getDateStringByTimestamp(
            date = dateOfBirthTimestamp,
            resources = resourceManager.getResources()
        )
    }

    private fun showProgress() {
        _progressBarVisibility.value = View.VISIBLE
    }

    private fun hideProgress() {
        _progressBarVisibility.value = View.GONE
    }

    companion object {
        private const val INITIAL_DATE_OF_BIRTH = 946684800000L
    }

}
