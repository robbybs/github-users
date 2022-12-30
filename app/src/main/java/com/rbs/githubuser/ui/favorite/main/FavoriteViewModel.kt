package com.rbs.githubuser.ui.favorite.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.rbs.githubuser.db.model.DetailUserDB
import com.rbs.githubuser.db.repository.DetailUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val detailUserRepository: DetailUserRepository = DetailUserRepository(application)

    fun getAllUsers(): LiveData<List<DetailUserDB>> = detailUserRepository.getAllFavoriteData()

    fun deleteData(username: String) {
        detailUserRepository.delete(username)
    }

    class ViewModelFactory(private val application: Application) :
        ViewModelProvider.NewInstanceFactory() {
        companion object {
            @Volatile
            private var INSTANCE: ViewModelFactory? = null

            @JvmStatic
            fun getInstance(application: Application): ViewModelFactory {
                if (INSTANCE == null) {
                    synchronized(ViewModelFactory::class.java) {
                        INSTANCE = ViewModelFactory(application)
                    }
                }
                return INSTANCE as ViewModelFactory
            }
        }

        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(FavoriteViewModel::class.java)) {
                return FavoriteViewModel(application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}