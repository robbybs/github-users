package com.rbs.githubuser.ui.main

import androidx.lifecycle.*
import com.rbs.githubuser.data.SearchUser
import com.rbs.githubuser.data.SearchUserResponse
import com.rbs.githubuser.utils.ApiConfig
import com.rbs.githubuser.utils.SettingPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchUserViewModel(private val pref: SettingPreference) : ViewModel() {
    val listUser = MutableLiveData<ArrayList<SearchUser>>()
    var listItems = ArrayList<SearchUser>()

    fun searchUsers(username: String) {
        val client = ApiConfig.getService().getUserResponse(username)
        client.enqueue(object : Callback<SearchUserResponse> {
            override fun onResponse(
                call: Call<SearchUserResponse>,
                searchUserResponse: Response<SearchUserResponse>
            ) {
                listItems = (searchUserResponse.body()?.userData as ArrayList<SearchUser>?)!!
                listUser.postValue(listItems)
            }

            override fun onFailure(call: Call<SearchUserResponse>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }

    fun getUser(): LiveData<ArrayList<SearchUser>> {
        return listUser
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    class ViewModelFactory(private val pref: SettingPreference) : ViewModelProvider.NewInstanceFactory() {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchUserViewModel::class.java)) {
                return SearchUserViewModel(pref) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}