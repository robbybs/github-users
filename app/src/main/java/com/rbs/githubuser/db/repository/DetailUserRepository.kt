package com.rbs.githubuser.db.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rbs.githubuser.db.DetailUserDB
import com.rbs.githubuser.db.DetailUserDao
import com.rbs.githubuser.db.RoomDB
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class DetailUserRepository(application: Application) {
    private val detailUserDao: DetailUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val database = RoomDB.getDatabase(application)
        detailUserDao = database.detailUserDao()
    }

    fun getAllFavoriteData(): LiveData<List<DetailUserDB>> = detailUserDao.getAllFavoriteData()

    fun insert(user: DetailUserDB) {
        executorService.execute { detailUserDao.insert(user) }
    }

    fun delete(username: String) {
        executorService.execute { detailUserDao.delete(username) }
    }

    fun getDataByUsername(username: String): LiveData<DetailUserDB> = detailUserDao.getDataByUsername(username)
}