package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.FavoriteProductsRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.AddFavoriteProductUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.GetAllFavoriteProductsUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.IsProductFavoriteUseCase
import com.epam.grocerystoreapp.domain.use_cases.favorite_products.RemoveFavoriteProductUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesFavoriteProductsModule {

    @Provides
    @Singleton
    fun provideAddFavoriteProductUseCase(
        favoriteProductsRepository: FavoriteProductsRepositoryImpl,
    ): AddFavoriteProductUseCase {
        return AddFavoriteProductUseCase(favoriteProductsRepository)
    }

    @Provides
    @Singleton
    fun provideGetAllFavoriteProductsUseCase(
        favoriteProductsRepository: FavoriteProductsRepositoryImpl,
    ): GetAllFavoriteProductsUseCase {
        return GetAllFavoriteProductsUseCase(favoriteProductsRepository)
    }

    @Provides
    @Singleton
    fun provideRemoveFavoriteProductUseCase(
        favoriteProductsRepository: FavoriteProductsRepositoryImpl,
    ): RemoveFavoriteProductUseCase {
        return RemoveFavoriteProductUseCase(favoriteProductsRepository)
    }

    @Provides
    @Singleton
    fun provideIsProductFavoriteUseCase(
        favoriteProductsRepository: FavoriteProductsRepositoryImpl,
    ): IsProductFavoriteUseCase {
        return IsProductFavoriteUseCase(favoriteProductsRepository)
    }

}
