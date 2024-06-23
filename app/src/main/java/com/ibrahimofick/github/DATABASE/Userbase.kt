package com.ibrahimofick.github.DATABASE

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class Userbase : RoomDatabase() {
    abstract fun DaoFavorite(): DaoFavorite

    companion object {
        @Volatile
        private var INSTANCE: Userbase? = null

        fun getDatabase(context: Context): Userbase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext, Userbase::class.java, "user_database"
                ).build().also { INSTANCE = it }
            }
        }
    }
}