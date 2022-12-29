package com.rbs.githubuser.ui.detail.followers

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.utils.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {
    val listUser = MutableLiveData<ArrayList<SearchUser>>()
    var listItems = ArrayList<SearchUser>()

    fun getFollowers(username: String) {
        val client = ApiConfig.getService().getFollowerUser(username)
        client.enqueue(object : Callback<List<SearchUser>> {
            override fun onResponse(
                call: Call<List<SearchUser>>,
                response: Response<List<SearchUser>>
            ) {
                listItems = (response.body() as ArrayList<SearchUser>?)!!
                listUser.postValue(listItems)
            }

            override fun onFailure(call: Call<List<SearchUser>>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getData(): LiveData<ArrayList<SearchUser>> {
        return listUser
    }
}