package com.rbs.mygithubuser.ui.favorite.presenter

import android.app.Application
import androidx.lifecycle.LiveData
import com.rbs.mygithubuser.base.BasePresenter
import com.rbs.mygithubuser.db.User
import com.rbs.mygithubuser.db.repository.UserRepository
import com.rbs.mygithubuser.ui.favorite.view.FavoriteView

class FavoritePresenter(application: Application) : BasePresenter<FavoriteView>() {
    private val repository = UserRepository(application)

    fun getData(): LiveData<List<User>> = repository.getAllUsers()

    fun deleteData(user: User) {
        repository.delete(user)
    }
}