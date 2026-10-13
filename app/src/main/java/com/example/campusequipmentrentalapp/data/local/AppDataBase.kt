package com.example.campusequipmentrentalapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [RentalEntity::class],
    version = 1,
    exportSchema = false
)

// abstract로 해야 RoomDataBase가 알아서 구현을 해줄 것임
abstract class AppDataBase: RoomDatabase() {
    /**
     * RentalDao 연결 처리
     */
    abstract fun rentalDao(): RentalDao

    //자바의 static과 유사함
    companion object{
        private var instance: AppDataBase? = null

        fun getDatabase(context: Context): AppDataBase {
            return instance ?: synchronized(this) {
                val _instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDataBase::class.java,
                    "campus_rental_database"
                ).build()
                instance = _instance
                _instance
            }
        }

    }
}