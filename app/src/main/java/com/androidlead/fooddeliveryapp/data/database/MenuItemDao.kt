package com.androidlead.fooddeliveryapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.androidlead.fooddeliveryapp.data.model.MenuItem

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM menu_item")
    suspend fun getAll(): List<MenuItem>

    @Insert
    suspend fun insertAll(vararg items: MenuItem)
}
