package com.example.campusequipmentrentalapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


//Dao는 interface여야 한다.
@Dao
interface RentalDao {

    @Insert
    suspend fun insert(rentalEntity: RentalEntity): Long

    @Query("SELECT * FROM rentals ORDER BY id DESC")
    suspend fun getAll(): List<RentalEntity>

    @Query("SELECT * FROM rentals WHERE userId =:userId")
    suspend fun getByUserId(userId: String): List<RentalEntity>?




}