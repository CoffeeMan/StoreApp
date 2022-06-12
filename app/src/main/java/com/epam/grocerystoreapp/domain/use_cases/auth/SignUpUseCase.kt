package com.epam.grocerystoreapp.domain.use_cases.auth

import com.epam.grocerystoreapp.domain.repository.AuthRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(
        name: String,
        surname: String,
        dateOfBirth: Long,
        email: String,
        password: String,
    ) {
        repository.signUp(
            name = name,
            surname = surname,
            dateOfBirth = dateOfBirth,
            email = email,
            password = password
        )
    }

}
