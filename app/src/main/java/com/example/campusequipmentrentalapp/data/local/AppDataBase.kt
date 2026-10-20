package com.example.campusequipmentrentalapp.data.local


import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [RentalEntity::class, UserEntity::class, EquipmentEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDataBase : RoomDatabase()
{
    /**
     * RentalDao 연결 처리
     * */
    abstract fun rentalDao(): RentalDao

    /**
     * UserDao 연결 처리
     * */
    abstract fun userDao(): UserDao

    /**
     * EquipmentDao 연결 처리
     * */
    abstract fun equipmentDao(): EquipmentDao


    companion object {
        private var instance: AppDataBase? = null

        /** 앱 전체에서 같은 DB 를 사용 */
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