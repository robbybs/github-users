package com.rbs.mygithubuser.ui.main.presenter

import com.rbs.mygithubuser.base.BasePresenter
import com.rbs.mygithubuser.data.search.SearchUserResponse
import com.rbs.mygithubuser.ui.main.view.MainView
import com.rbs.mygithubuser.utils.ApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter : BasePresenter<MainView>() {

    private lateinit var service: ApiRequest

    fun initialization() {
        if (isViewAttached()) getView()?.setService()
    }

    fun setService(service: ApiRequest) {
        this.service = service
    }

    private fun getUserResponse(username: String) = service.getUserResponse(username)

    fun getUserData(username: String) {
        getUserResponse(username).enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                response: Response<SearchUserResponse>
            ) {
                val userData = response.body()?.userData
                if (isViewAttached()) getView()?.stopLoading()
                if (isViewAttached()) getView()?.setData(userData)
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                if (isViewAttached()) getView()?.setErrorMessage(t.message.toString())
            }
        })
    }
}