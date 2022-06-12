package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.onboarding.OnboardingItem

interface OnboardingRepository {
    fun getOnboardingSteps() : List<OnboardingItem>
}