package com.epam.grocerystoreapp.data.repository

import com.epam.grocerystoreapp.R
import com.epam.grocerystoreapp.domain.model.onboarding.OnboardingItem
import com.epam.grocerystoreapp.domain.repository.OnboardingRepository
import javax.inject.Inject

class OnboardingRepositoryImpl @Inject constructor() : OnboardingRepository {
    override fun getOnboardingSteps(): List<OnboardingItem> {
        return listOf(
            OnboardingItem(
                R.drawable.gif_choose_product,
                R.string.onboarding_first_screen_title,
                R.string.onboarding_first_screen_description
            ),
            OnboardingItem(
                R.drawable.gif_scan_product,
                R.string.onboarding_second_screen_title,
                R.string.onboarding_second_screen_description
            ),
            OnboardingItem(
                R.drawable.gif_pay_for_the_product,
                R.string.onboarding_third_screen_title,
                R.string.onboarding_third_screen_description
            )
        )
    }
}