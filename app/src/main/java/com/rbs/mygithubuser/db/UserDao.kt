package com.rbs.mygithubuser.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(user: User)

    @Update
    fun update(user: User)

    @Delete
    fun delete(user: User)

    @Query("SELECT * from user ORDER BY id ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Query("SELECT * from user WHERE username = :username")
    fun getUserByUsername(username: String): LiveData<User>
}