package com.epam.grocerystoreapp.domain.repository

import com.epam.grocerystoreapp.domain.model.User

interface AuthRepository {
    suspend fun signUp(
        name: String,
        surname: String,
        dateOfBirth: Long,
        email: String,
        password: String,
    )

    suspend fun signIn(email: String, password: String)
    suspend fun signOut()
    suspend fun restorePassword(email: String)
    suspend fun getUser(): User
    suspend fun getAuthState(): Boolean
    suspend fun editUser(name: String, surname: String, dateOfBirth: Long)
}
