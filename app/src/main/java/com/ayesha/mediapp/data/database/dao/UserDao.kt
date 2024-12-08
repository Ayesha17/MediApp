package com.ayesha.mediapp.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayesha.mediapp.data.database.entities.User

@Dao
interface UserDao {

    //for single user insert
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User): Long

    //checking user exist or not in our db
    @Query("SELECT * FROM User order by id desc limit 1")
    fun readLoginData( ):User

    //deleting all user from db
    @Query("DELETE FROM User")
    fun deleteAll()



}