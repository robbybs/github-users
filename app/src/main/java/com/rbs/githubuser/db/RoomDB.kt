package com.rbs.githubuser.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DetailUserDB::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun detailUserDao(): DetailUserDao

    companion object {
        @Volatile
        private var INSTANCE: RoomDB? = null

        @JvmStatic
        fun getDatabase(context: Context): RoomDB {
            if (INSTANCE == null) {
                synchronized(RoomDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        RoomDB::class.java, "user_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE as RoomDB
        }
    }
}