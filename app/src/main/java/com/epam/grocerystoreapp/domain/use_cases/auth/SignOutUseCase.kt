package com.epam.grocerystoreapp.domain.use_cases.auth

import com.epam.grocerystoreapp.domain.repository.AuthRepository
import javax.inject.Inject

class SignOutUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke() {
        repository.signOut()
    }

}
