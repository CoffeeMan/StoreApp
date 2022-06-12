package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.CatalogRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.catalog.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesCatalogModule {

    @Provides
    @Singleton
    fun provideGetAllProductsUseCase(
        catalogRepository: CatalogRepositoryImpl,
    ): GetAllProductsUseCase {
        return GetAllProductsUseCase(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductsByCodeUseCase(
        catalogRepository: CatalogRepositoryImpl,
    ): GetProductByCodeUseCase {
        return GetProductByCodeUseCase(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductsByCategoryUseCase(
        catalogRepository: CatalogRepositoryImpl,
    ): GetProductsByCategoryUseCase {
        return GetProductsByCategoryUseCase(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductsByTitleUseCase(
        catalogRepository: CatalogRepositoryImpl,
    ): GetProductsByNameUseCase {
        return GetProductsByNameUseCase(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideReverseFavoriteMarkUseCase(
        catalogRepository: CatalogRepositoryImpl,
    ): ReverseFavoriteMarkUseCase {
        return ReverseFavoriteMarkUseCase(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideLoadProductsUseCase(
        catalogRepository: CatalogRepositoryImpl,
    ): LoadProductsUseCase {
        return LoadProductsUseCase(catalogRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllCategories(
        catalogRepository: CatalogRepositoryImpl,
    ): GetAllCategoriesUseCase {
        return GetAllCategoriesUseCase(catalogRepository)
    }

}
