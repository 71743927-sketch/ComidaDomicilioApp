package com.androidlead.fooddeliveryapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.androidlead.fooddeliveryapp.data.local.UserDao
import com.androidlead.fooddeliveryapp.data.model.CartItem
import com.androidlead.fooddeliveryapp.data.model.MenuItem
import com.androidlead.fooddeliveryapp.data.model.User

@Database(entities = [MenuItem::class, CartItem::class, User::class], version = 9)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
    abstract fun cartItemDao(): CartItemDao
    abstract fun userDao(): UserDao
}
