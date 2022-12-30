package com.rbs.githubuser.db

import android.content.Context
import androidx.room.*
import com.rbs.githubuser.db.dao.DetailUserDao
import com.rbs.githubuser.db.model.DetailUserDB

@TypeConverters(value = [CustomTypeConverter::class])
@Database(entities = [DetailUserDB::class], version = 2)
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