package com.epam.grocerystoreapp.domain.model

data class User(
    val id: String = "",
    val name: String = "",
    val surname: String = "",
    val dateOfBirth: Long = 0,
    val email: String = "",
)
