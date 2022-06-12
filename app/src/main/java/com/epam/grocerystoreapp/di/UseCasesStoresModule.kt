package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.StoresRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.stores.GetAllStoresUseCase
import com.epam.grocerystoreapp.domain.use_cases.stores.GetStoreByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesStoresModule {

    @Provides
    @Singleton
    fun provideGetAllStoresUseCase(
        storesRepository: StoresRepositoryImpl,
    ): GetAllStoresUseCase {
        return GetAllStoresUseCase(storesRepository)
    }

    @Provides
    @Singleton
    fun provideGetStoreByIdUseCase(
        storesRepository: StoresRepositoryImpl,
    ): GetStoreByIdUseCase {
        return GetStoreByIdUseCase(storesRepository)
    }

}
