package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.ScanRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.scan.GetProductByBarcodeUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesScanModule {

    @Provides
    @Singleton
    fun provideGetProductByBarcodeUseCase(
        scanRepository: ScanRepositoryImpl,
    ): GetProductByBarcodeUseCase {
        return GetProductByBarcodeUseCase(scanRepository)
    }

}
