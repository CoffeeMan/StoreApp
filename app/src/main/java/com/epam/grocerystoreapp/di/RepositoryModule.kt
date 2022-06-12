package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.api.ApiProductService
import com.epam.grocerystoreapp.data.database.cart.CartDao
import com.epam.grocerystoreapp.data.database.catalog.CatalogDao
import com.epam.grocerystoreapp.data.network.ProductsPageSource
import com.epam.grocerystoreapp.data.repository.AuthRepositoryImpl
import com.epam.grocerystoreapp.data.repository.CartRepositoryImpl
import com.epam.grocerystoreapp.data.repository.CatalogRepositoryImpl
import com.epam.grocerystoreapp.data.repository.ProductRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firebaseDatabase: FirebaseDatabase,
    ): AuthRepositoryImpl {
        return AuthRepositoryImpl(firebaseAuth = firebaseAuth, firebaseDatabase = firebaseDatabase)
    }

    @Provides
    @Singleton
    fun provideCatalogRepository(
        catalogDao: CatalogDao,
        apiProductService: ApiProductService,
    ): CatalogRepositoryImpl {
        return CatalogRepositoryImpl(catalogDao = catalogDao, apiProductService = apiProductService)
    }

    @Provides
    @Singleton
    fun provideCartRepository(
        cartDao: CartDao,
        apiProductService: ApiProductService,
    ): CartRepositoryImpl {
        return CartRepositoryImpl(cartDao = cartDao, apiProductService = apiProductService)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        apiProductService: ApiProductService,
    ): ProductRepositoryImpl {
        return ProductRepositoryImpl(apiProductService = apiProductService)
    }

}
