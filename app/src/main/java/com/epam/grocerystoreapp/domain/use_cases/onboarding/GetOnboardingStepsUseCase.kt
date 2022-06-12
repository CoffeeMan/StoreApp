package com.epam.grocerystoreapp.domain.use_cases.onboarding

import com.epam.grocerystoreapp.domain.model.onboarding.OnboardingItem
import com.epam.grocerystoreapp.domain.repository.OnboardingRepository
import javax.inject.Inject

class GetOnboardingStepsUseCase @Inject constructor(
    private val repository: OnboardingRepository
) {
    operator fun invoke(): List<OnboardingItem> = repository.getOnboardingSteps()
}