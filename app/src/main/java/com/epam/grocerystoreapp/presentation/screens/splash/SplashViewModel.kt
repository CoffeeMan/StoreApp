package com.epam.grocerystoreapp.presentation.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.use_cases.auth.GetAuthStateUseCase
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
) : ViewModel() {

    private val navigationIdSingleEvent = SingleLiveEvent<Int>()
    fun navigationId() = navigationIdSingleEvent

    init {
        getAuthState()
    }

    private fun getAuthState() {
        viewModelScope.launch {
            val authState = withContext(Dispatchers.IO) {
                getAuthStateUseCase()
            }

            delay(DELAY_TIME)

            if (authState) {
                navigationIdSingleEvent.value = R.id.action_splashFragment_to_homeFragment
            } else {
                navigationIdSingleEvent.value = R.id.action_splashFragment_to_onboardingFragment
            }
        }
    }

    companion object {
        private const val DELAY_TIME = 3000L
    }

}
