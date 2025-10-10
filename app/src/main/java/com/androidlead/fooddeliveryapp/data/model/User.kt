package com.androidlead.fooddeliveryapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey
    val email: String,
    val passwordHash: String
)
