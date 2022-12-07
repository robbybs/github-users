package com.rbs.mygithubuser.ui.favorite.detail.presenter

import android.app.Application
import androidx.lifecycle.LiveData
import com.rbs.mygithubuser.base.BasePresenter
import com.rbs.mygithubuser.db.User
import com.rbs.mygithubuser.db.repository.UserRepository
import com.rbs.mygithubuser.ui.favorite.detail.view.DetailFavoriteView

class DetailFavoritePresenter(application: Application) : BasePresenter<DetailFavoriteView>() {

    private val repository = UserRepository(application)

    fun getData(username: String): LiveData<User> = repository.getUserByUsername(username)
}