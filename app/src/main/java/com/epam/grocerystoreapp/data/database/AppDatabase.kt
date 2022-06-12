package com.epam.grocerystoreapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.epam.grocerystoreapp.data.database.cart.CartDao
import com.epam.grocerystoreapp.data.database.cart.CartEntity
import com.epam.grocerystoreapp.data.database.catalog.CatalogDao
import com.epam.grocerystoreapp.data.database.catalog.CatalogEntity

@Database(
    version = 1,
    entities = [
        CatalogEntity::class,
        CartEntity::class,
    ]
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun cartDao(): CartDao
    abstract fun catalogDao(): CatalogDao
}
