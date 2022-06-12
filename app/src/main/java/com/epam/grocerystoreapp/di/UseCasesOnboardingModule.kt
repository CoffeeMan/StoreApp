package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.OnboardingRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.onboarding.GetOnboardingStepsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesOnboardingModule {

    @Provides
    @Singleton
    fun provideGetOnboardingStepsUseCase(
        onboardingRepository: OnboardingRepositoryImpl
    ) : GetOnboardingStepsUseCase {
        return GetOnboardingStepsUseCase(onboardingRepository)
    }
}