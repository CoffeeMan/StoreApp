package com.epam.grocerystoreapp.domain.model

data class Store(
    val id: String = "",
    val name: String = "",
    val locality: String = "",
    val address: String = "",
    val schedule: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
)
