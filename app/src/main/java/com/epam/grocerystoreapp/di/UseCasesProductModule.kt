package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.ProductRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.product.GetProductByIdUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesProductModule {

    @Provides
    @Singleton
    fun provideGetProductByIdUseCase(
        repository: ProductRepositoryImpl,
    ): GetProductByIdUseCase {
        return GetProductByIdUseCase(repository)
    }

}
