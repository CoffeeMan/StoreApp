package com.epam.grocerystoreapp.domain.use_cases.auth

import com.epam.grocerystoreapp.domain.repository.AuthRepository
import javax.inject.Inject

class EditUserUseCase @Inject constructor(
    private val repository: AuthRepository,
) {

    suspend operator fun invoke(
        name: String,
        surname: String,
        dateOfBirth: Long,
    ) {
        return repository.editUser(
            name = name,
            surname = surname,
            dateOfBirth = dateOfBirth
        )
    }

}