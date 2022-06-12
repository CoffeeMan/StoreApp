package com.epam.grocerystoreapp.data.database.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Query("SELECT * FROM cart_entities WHERE cart_entity_id = :id")
    suspend fun getCartEntityById(id: String): CartEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCartEntity(cartEntity: CartEntity)

    @Query("DELETE FROM cart_entities WHERE cart_entity_id = :id")
    suspend fun deleteCartEntity(id: String)

    @Query("UPDATE cart_entities SET quantity = quantity + :amount WHERE cart_entity_id = :id")
    suspend fun increaseQuantity(id: String, amount: Double)

    @Query("UPDATE cart_entities SET quantity = quantity - :amount WHERE cart_entity_id = :id")
    suspend fun decreaseQuantity(id: String, amount: Double)

    @Query("DELETE FROM cart_entities")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_entities")
    suspend fun getAllCartEntities(): List<CartEntity>

}
