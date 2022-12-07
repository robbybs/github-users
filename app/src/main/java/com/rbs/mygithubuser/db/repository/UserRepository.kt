package com.rbs.mygithubuser.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rbs.mygithubuser.db.User
import com.rbs.mygithubuser.db.UserDao
import com.rbs.mygithubuser.db.UserRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class UserRepository(application: Application) {
    private val usersDao: UserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = UserRoomDatabase.getDatabase(application)
        usersDao = db.userDao()
    }

    fun getAllUsers(): LiveData<List<User>> = usersDao.getAllUsers()

    fun insert(user: User) {
        executorService.execute { usersDao.insert(user) }
    }

    fun delete(user: User) {
        executorService.execute { usersDao.delete(user) }
    }

    fun getUserByUsername(username: String): LiveData<User> = usersDao.getUserByUsername(username)
}