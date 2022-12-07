package com.rbs.mygithubuser.ui.detail.presenter

import com.rbs.mygithubuser.base.BasePresenter
import com.rbs.mygithubuser.data.search.SearchUserItems
import com.rbs.mygithubuser.ui.detail.view.FollowingView
import com.rbs.mygithubuser.utils.ApiRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingPresenter : BasePresenter<FollowingView>() {

    private lateinit var service: ApiRequest

    fun initialization() {
        if (isViewAttached()) getView()?.setService()
    }

    fun setService(service: ApiRequest) {
        this.service = service
    }

    private fun getFollowingResponse(username: String) = service.getFollowingUser(username)

    fun getUserData(username: String) {
        getFollowingResponse(username).enqueue(object : Callback<List<SearchUserItems>> {
            override fun onResponse(
                call: Call<List<SearchUserItems>>,
                response: Response<List<SearchUserItems>>
            ) {
                val userData = response.body()
                if (isViewAttached()) getView()?.stopLoading()
                if (userData != null) {
                    if (isViewAttached()) getView()?.setData(userData)
                }
            }

            override fun onFailure(call: Call<List<SearchUserItems>>, t: Throwable) {
                if (isViewAttached()) getView()?.setErrorMessage(t.message.toString())
            }
        })
    }
}