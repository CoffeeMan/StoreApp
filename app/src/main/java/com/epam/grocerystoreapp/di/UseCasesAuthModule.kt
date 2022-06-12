package com.epam.grocerystoreapp.di

import com.epam.grocerystoreapp.data.repository.AuthRepositoryImpl
import com.epam.grocerystoreapp.domain.use_cases.auth.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCasesAuthModule {

    @Provides
    @Singleton
    fun provideGetAuthStateUseCase(
        authRepository: AuthRepositoryImpl,
    ): GetAuthStateUseCase {
        return GetAuthStateUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideGetUserUseCase(
        authRepository: AuthRepositoryImpl,
    ): GetUserUseCase {
        return GetUserUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideRestorePasswordUseCase(
        authRepository: AuthRepositoryImpl,
    ): RestorePasswordUseCase {
        return RestorePasswordUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignInUseCase(
        authRepository: AuthRepositoryImpl,
    ): SignInUseCase {
        return SignInUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignOutUseCase(
        authRepository: AuthRepositoryImpl,
    ): SignOutUseCase {
        return SignOutUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideSignUpUseCase(
        authRepository: AuthRepositoryImpl,
    ): SignUpUseCase {
        return SignUpUseCase(authRepository)
    }

    @Provides
    @Singleton
    fun provideEditUserUseCase(
        authRepository: AuthRepositoryImpl,
    ): EditUserUseCase {
        return EditUserUseCase(authRepository)
    }

}
