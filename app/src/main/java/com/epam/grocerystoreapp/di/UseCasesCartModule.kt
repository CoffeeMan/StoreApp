package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.CartRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.cart.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesCartModule {

    @Provides
    @Singleton
    fun provideGetAllProductsInCartUseCase(
        cartRepository: CartRepositoryImpl,
    ): GetAllCartItemsUseCase {
        return GetAllCartItemsUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideAddProductToCartUseCase(
        cartRepository: CartRepositoryImpl,
    ): AddCartItemUseCase {
        return AddCartItemUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideDeleteProductFromCartUseCase(
        cartRepository: CartRepositoryImpl,
    ): DeleteCartItemUseCase {
        return DeleteCartItemUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideIncreaseProductQuantityUseCase(
        cartRepository: CartRepositoryImpl,
    ): IncreaseCartItemUseCase {
        return IncreaseCartItemUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideDecreaseProductQuantityUseCase(
        cartRepository: CartRepositoryImpl,
    ): DecreaseCartItemUseCase {
        return DecreaseCartItemUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideClearCartUseCase(
        cartRepository: CartRepositoryImpl,
    ): ClearCartUseCase {
        return ClearCartUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideGetCartCostUseCase(
        cartRepository: CartRepositoryImpl,
    ): GetCartCostUseCase {
        return GetCartCostUseCase(cartRepository)
    }

    @Provides
    @Singleton
    fun provideGetProductQuantityInCartUseCase(
        cartRepository: CartRepositoryImpl,
    ): GetProductQuantityInCartUseCase {
        return GetProductQuantityInCartUseCase(cartRepository)
    }

}
