package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.HomeRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.home.GetNewsItemsUseCase
import com.epam.grocerystoreapp.domain.use_cases.home.GetPromoItemsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseHomeModule {

    @Provides
    @Singleton
    fun provideGetNewsItemsUseCase(
        repository: HomeRepositoryImpl,
    ): GetNewsItemsUseCase {
        return GetNewsItemsUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideGetPromoItemsUseCase(
        repository: HomeRepositoryImpl,
    ): GetPromoItemsUseCase {
        return GetPromoItemsUseCase(repository)
    }

}
