package com.epam.grocerystoreapp.presentation.screens.profile.profile_edit

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.auth.EditUserUseCase
import com.epam.grocerystoreapp.domain.use_cases.auth.GetUserUseCase
import com.epam.grocerystoreapp.presentation.model.DatePickerData
import com.epam.grocerystoreapp.presentation.model.UserUi
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDateStringByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getDayIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getMonthIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getTimestampGmtByInt
import com.epam.grocerystoreapp.presentation.utils.CalendarUtils.getYearIntCurrentTimezoneByTimestamp
import com.epam.grocerystoreapp.presentation.utils.Const.APP_LOG_TAG
import com.epam.grocerystoreapp.presentation.utils.Const.NO_INTERNET_EXCEPTION_MESSAGE
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.getCapitalizedString
import com.epam.grocerystoreapp.presentation.utils.PresentationUtils.isValidName
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
class ProfileEditViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase,
    private val editUserUseCase: EditUserUseCase,
    private val resourceManager: ResourceManager,
) : ViewModel() {

    private val _progressBarVisibility = MutableLiveData<Int>()
    val progressBarVisibility: LiveData<Int> = _progressBarVisibility

    private val popBackStackSingleEvent = SingleLiveEvent<Boolean>()
    fun popBackStack() = popBackStackSingleEvent

    private val _userUi = MutableLiveData<UserUi>()
    val userUi: LiveData<UserUi> = _userUi

    private val _datePickerData = MutableLiveData<DatePickerData>(null)
    val datePickerData: LiveData<DatePickerData> = _datePickerData

    private val _dateOfBirth = MutableLiveData<String>(null)
    val dateOfBirth: LiveData<String> = _dateOfBirth

    private val toastResIdSingleEvent = SingleLiveEvent<Int>()
    fun toastResId() = toastResIdSingleEvent

    private var dateOfBirthTimestamp = 0L

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

    init {
        getUser()
    }

    fun onApplyChangesClicked(
        surname: String,
        name: String,
        dateOfBirth: String,
    ) {
        viewModelScope.launch(exceptionHandler) {
            showProgress()

            if (!isValidName(name)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_name
            } else if (!isValidName(surname)) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_surname
            } else if (dateOfBirth.isBlank()) {
                toastResIdSingleEvent.value = R.string.auth_hint_invalid_date_of_birth
            } else {
                val capitalizedName = getCapitalizedString(name)
                val capitalizedSurname = getCapitalizedString(surname)

                withContext(Dispatchers.IO) {
                    editUserUseCase(
                        name = capitalizedName,
                        surname = capitalizedSurname,
                        dateOfBirth = dateOfBirthTimestamp,
                    )
                }

                popBackStackSingleEvent.value = true
            }

            hideProgress()
        }
    }

    private fun getUser() {
        viewModelScope.launch(exceptionHandler) {
            showProgress()

            val user = withContext(Dispatchers.IO) {
                getUserUseCase()
            }

            val userUi = user.toUserUi(resources = resourceManager.getResources())

            dateOfBirthTimestamp = user.dateOfBirth
            _userUi.value = userUi
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
            resources = resourceManager.getResources(),
            date = dateOfBirthTimestamp,
        )
    }

    private fun showProgress() {
        _progressBarVisibility.value = View.VISIBLE
    }

    private fun hideProgress() {
        _progressBarVisibility.value = View.GONE
    }

}