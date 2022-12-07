package com.rbs.mygithubuser.ui.detail.presenter

import android.app.Application
import androidx.lifecycle.LiveData
import com.rbs.mygithubuser.base.BasePresenter
import com.rbs.mygithubuser.data.detail.DetailUser
import com.rbs.mygithubuser.db.User
import com.rbs.mygithubuser.db.repository.UserRepository
import com.rbs.mygithubuser.ui.detail.view.DetailView
import com.rbs.mygithubuser.utils.ApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(application: Application) : BasePresenter<DetailView>() {

    private lateinit var service: ApiRequest
    private lateinit var username: String
    private val repository = UserRepository(application)

    fun initialization() {
        if (isViewAttached()) getView()?.setService()

    }

    fun setService(service: ApiRequest) {
        this.service = service
    }

    fun setUsername(username: String) {
        this.username = username
    }

    private fun getDetailUser(username: String) = service.getDetailUser(username)

    fun getDetailUserData(username: String) {
        getDetailUser(username).enqueue(object : Callback<DetailUser> {
            override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                val userData = response.body()
                if (isViewAttached()) {
                    getView()?.stopLoading()
                    getView()?.setData(userData)
                }
            }

            override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                if (isViewAttached()) getView()?.setErrorMessage(t.message.toString())
            }
        })
    }

    fun insertData(user: User) {
        repository.insert(user)
    }
}