package com.androidlead.fooddeliveryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidlead.fooddeliveryapp.data.model.CartItem
import com.androidlead.fooddeliveryapp.data.model.MenuItem

@Database(entities = [MenuItem::class, CartItem::class], version = 8)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
    abstract fun cartItemDao(): CartItemDao
}
