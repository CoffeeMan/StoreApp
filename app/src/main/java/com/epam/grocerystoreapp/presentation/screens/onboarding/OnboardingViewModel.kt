package com.epam.grocerystoreapp.presentation.screens.onboarding

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.onboarding.OnboardingItem
import com.epam.grocerystoreapp.domain.use_cases.onboarding.GetOnboardingStepsUseCase
import com.epam.grocerystoreapp.presentation.utils.SingleLiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    getOnboardingStepsUseCase: GetOnboardingStepsUseCase
) : ViewModel() {

    private val _steps = MutableLiveData<List<OnboardingItem>>()
    val steps : LiveData<List<OnboardingItem>> = _steps

    private val _currentStepId = MutableLiveData(0)
    val currentStepId : LiveData<Int> = _currentStepId

    private val navigationIdSingleEvent = SingleLiveEvent<Int>()
    fun navigationId() = navigationIdSingleEvent

    init {
        _steps.value = getOnboardingStepsUseCase()
    }

    fun stepSelected(stepPosition: Int) {
        _currentStepId.value = stepPosition
    }

    fun nextStep() {
        _currentStepId.value?.let { stepId ->
            steps.value?.let { stepsValue ->
                if (stepsValue.size > stepId + 1)
                    _currentStepId.value = stepId + 1
                else
                    navigationIdSingleEvent.value = R.id.action_onboardingFragment_to_startAuthFragment
            }
        }
    }
}