package com.androidlead.fooddeliveryapp

import android.app.Application
import androidx.room.Room
import com.androidlead.fooddeliveryapp.data.database.AppDatabase

class FoodDeliveryApplication : Application() {

    lateinit var database: AppDatabase

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "food-delivery-db"
        ).fallbackToDestructiveMigration().build()
    }
}
