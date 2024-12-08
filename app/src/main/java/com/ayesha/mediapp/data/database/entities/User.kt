package com.ayesha.mediapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "User")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    var userName: String? = "",
    var email: String? = "",
)