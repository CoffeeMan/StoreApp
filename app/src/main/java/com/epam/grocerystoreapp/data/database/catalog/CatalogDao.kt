package com.epam.grocerystoreapp.data.database.catalog

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.epam.grocerystoreapp.domain.model.ProductCategory

@Dao
interface CatalogDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogEntities(catalogEntities: List<CatalogEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCatalogEntity(catalogEntity: CatalogEntity)

    @Query("SELECT * FROM catalog_entities WHERE catalog_entity_id LIKE :id")
    suspend fun getCatalogEntityById(id: String): CatalogEntity

    @Query("SELECT * FROM catalog_entities")
    suspend fun getAllCatalogEntities(): List<CatalogEntity>

    @Query("SELECT * FROM catalog_entities WHERE name LIKE '%' || :searchQuery || '%'")
    suspend fun getCatalogEntitiesByName(searchQuery: String): List<CatalogEntity>

    @Query("SELECT * FROM catalog_entities WHERE category LIKE :category")
    suspend fun getCatalogEntitiesByCategory(category: ProductCategory): List<CatalogEntity>

    @Query("SELECT * FROM catalog_entities WHERE is_favorite LIKE '1'")
    suspend fun getFavoriteCatalogEntities(): List<CatalogEntity>

    @Query("SELECT category FROM catalog_entities GROUP BY category")
    suspend fun getAllCategories(): List<String>

}