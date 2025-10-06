package com.androidlead.fooddeliveryapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.androidlead.fooddeliveryapp.data.model.CartItem
import kotlinx.coroutines.flow.Flow

@Dao
interface CartItemDao {

    @Query("SELECT * FROM cart_item")
    fun getCartItems(): Flow<List<CartItem>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(cartItem: CartItem)

    @Update
    suspend fun update(cartItem: CartItem)

    @Query("DELETE FROM cart_item WHERE id = :itemId")
    suspend fun delete(itemId: Int)

    @Query("DELETE FROM cart_item")
    suspend fun clearCart()

    @Query("SELECT * FROM cart_item WHERE menuItemId = :menuItemId LIMIT 1")
    suspend fun getCartItemByMenuItemId(menuItemId: Int): CartItem?
}
