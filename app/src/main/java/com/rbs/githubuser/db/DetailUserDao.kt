package com.rbs.githubuser.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface DetailUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: DetailUserDB)

    @Query("DELETE FROM detailuserdb WHERE username = :username")
    fun delete(username: String)

    @Query("SELECT * from detailuserdb ORDER BY id ASC")
    fun getAllFavoriteData(): LiveData<List<DetailUserDB>>

    @Query("SELECT * FROM detailuserdb WHERE username = :username")
    fun getDataByUsername(username: String): LiveData<DetailUserDB>
}