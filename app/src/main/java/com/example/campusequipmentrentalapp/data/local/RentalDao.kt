package com.example.campusequipmentrentalapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


//Dao는 interface여야 한다.
@Dao
interface RentalDao {

    @Insert
    fun insert(rentalEntity: RentalEntity): Long

    @Query("SELECT * FROM rentals ORDER BY id DESC")
    fun getAll(): List<RentalEntity>

    @Query("SELECT * FROM rentals WHERE userId =:userId")
    fun getByUserId(userId: Int): List<RentalEntity>?




}