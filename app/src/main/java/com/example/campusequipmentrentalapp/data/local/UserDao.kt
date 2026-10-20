package com.example.campusequipmentrentalapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    suspend fun insert(userEntity: UserEntity): Long

    @Query("SELECT * FROM users ORDER BY id DESC")
    suspend fun getAll(): List<RentalEntity>

    @Query("SELECT * FROM USERS WHERE userId = :userId ORDER BY id DESC LIMIT 1")
    suspend fun getByLoginId(userId: String): UserEntity?
}